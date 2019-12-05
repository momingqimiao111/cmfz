package com.baizhi.aspect;


import com.alibaba.fastjson.JSON;
import com.baizhi.annotation.LogAnnotation;
import com.baizhi.entity.MapVO;
import com.baizhi.service.UserService;
import com.fasterxml.jackson.annotation.JacksonAnnotation;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Aspect
@Configuration
public class LogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private UserService userService;

    @Around("@annotation(com.baizhi.annotation.LogAnnotation)")
    public Object addLog(ProceedingJoinPoint proceedingJoinPoint){
        //获取session
        HttpSession session = request.getSession();
        //获取时间
        Date date = new Date();
        //获取用户名
        String admin = (String) session.getAttribute("admin");
        //获取方法名
        String name = proceedingJoinPoint.getSignature().getName();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
        String value = annotation.value();
        System.out.println(value);
        try {
            Object proceed = proceedingJoinPoint.proceed();
            String status = "success";
            System.out.println(admin+" "+date+" "+name+" "+status);
            return proceed;
        } catch (Throwable throwable) {
            String status = "error";
            System.out.println(admin+" "+date+" "+name+" "+status);
            throwable.printStackTrace();
            return null;
        }
    }
    @Around("@annotation(com.baizhi.annotation.MapAnnotation)")
    public Object mapLog(ProceedingJoinPoint proceedingJoinPoint){
        try {
            Object proceed = proceedingJoinPoint.proceed();
            List<MapVO> mapVOS = userService.queryCountLocation();
            String jsonString = JSON.toJSONString(mapVOS);
            GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-7678885b4d8443f6abe98b0fe45edaf2");
            goEasy.publish("cmfz",jsonString, new PublishListener(){
                @Override
                public void onSuccess() {
                    System.out.print("Publish message success.");
                }
                @Override
                public void onFailed(GoEasyError error) {
                    System.out.print("Publish message failed, Error code:" + error.getCode() + " Error message: " + error.getContent());
                }
            });
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }






}
