package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.SecurityCode;
import com.baizhi.util.SecurityImage;
import com.sun.deploy.net.URLEncoder;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("getCode")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String securityCode = SecurityCode.getSecurityCode();
        HttpSession session = request.getSession();
        session.setAttribute("securityCode", securityCode);
        BufferedImage createImage = SecurityImage.createImage(securityCode);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(createImage, "png", outputStream);
    }
    @RequestMapping("login")
    @ResponseBody
    public String login(Admin admin,HttpServletRequest request,String code) throws UnsupportedEncodingException {
        HttpSession session = request.getSession();
        String securityCode = (String) session.getAttribute("securityCode");
        if (securityCode.equalsIgnoreCase(code)){
           try {
               adminService.login(admin);
               session.setAttribute("admin",admin.getUsername());
               return "success";
           }catch (Exception e){
               return e.getMessage();
           }

        }else{

            return "验证码错误!";
        }
    }


}
