package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.entity.MapVO;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.util.MD5Utils;
import com.baizhi.util.SendMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("showUser")
    public Map<String, Object> showUser(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        List<User> lists = null;
        Integer records = null;

        records = userService.getRecords();
        lists = userService.selectByPage((page - 1) * rows, rows);


        map.put("page", page);
        map.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        map.put("records", records);
        map.put("rows", lists);
        return map;
    }
    @RequestMapping("edit")
    public void edit(User user) {
        user.setStatus(user.getStatus().equals("正常") ? "冻结" : "正常");
        userService.updateUser(user);
    }
    @RequestMapping("queryEcharts")
    public Map<String,List<Integer>> queryEcharts(){
        Integer man1 = userService.queryUserBySexAndDate("男", 1);
        Integer man2 = userService.queryUserBySexAndDate("男", 7);
        Integer man3 = userService.queryUserBySexAndDate("男", 30);
        Integer man4 = userService.queryUserBySexAndDate("男", 365);
        Integer woman1 = userService.queryUserBySexAndDate("女", 1);
        Integer woman2 = userService.queryUserBySexAndDate("女", 7);
        Integer woman3 = userService.queryUserBySexAndDate("女", 30);
        Integer woman4 = userService.queryUserBySexAndDate("女", 365);
        List<Integer> manList = Arrays.asList(man1, man2, man3, man4);
        List<Integer> womanList = Arrays.asList(woman1, woman2, woman3, woman4);
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("男", manList);
        map.put("女", womanList);
        return map;


    }
    @RequestMapping("showLocation")
    public List<MapVO> showLocation(){
        List<MapVO> mapVOS = userService.queryCountLocation();
        return mapVOS;
    }
    @RequestMapping("login")
    public Map<String, Object> login(User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            User login = userService.login(user);
            map.put("status", "200");
            map.put("user", login);
            return map;
        }catch (Exception e){
            map.put("status", "-200");
            map.put("message", e.getMessage());
            return map;
        }
    }
    @RequestMapping("sendCode")
    public Map<String,Object> sendCode(String phone){
        Map<String, Object> map = new HashMap<>();
        try {
            //生成验证码
            String num = MD5Utils.getCode();
            //调用xxx发送验证码
            SendMessageUtil.sendSms(phone,num);
            //使用redis保存验证码
            ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
            stringStringValueOperations.set(phone,num,300, TimeUnit.SECONDS);
            //返回
            map.put("status", "200");
            map.put("phone", phone);
            return map;

        }catch (Exception e){
            map.put("status", "-200");
            map.put("message", "验证码发送失败!");
            return map;
        }
    }

    //注册
    @RequestMapping("regist")
    public Map<String, Object> regist(String phone, String code) {
        Map<String, Object> map = new HashMap<>();
        //1.从redis里获取code
        ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();
        String phone1 = stringStringValueOperations.get(phone);
        //2.判断code是否存在
        if (phone1==null){
            map.put("status", "-200");
            map.put("message", "验证码不存在或已过期!");
            return map;
        }else if (phone1.equals(code)){
            User user = new User();
            user.setId(UUID.randomUUID().toString()).setPhone(phone).setRigest_date(new Date());
            userService.addUser(user);
            map.put("status", "200");
            map.put("user", user);
            return map;
        }else {
            map.put("status", "-200");
            map.put("message", "验证码不正确!");
            return map;
        }
        //3.判断code是否相等
        //4.调用添加方法将数据添加到数据库
        //返回信息


    }
    @RequestMapping("editUser")
    public Map<String, Object> editUser(User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            userService.updateUser(user);
            User user1 = userService.selectOne(user);
            map.put("status", "200");
            map.put("user", user1);
            return map;
        }catch (Exception e){
            map.put("status", "-200");
            map.put("message", "更新失败!");
            return map;
        }
    }

    @RequestMapping("updateUser")
    public Map<String, Object> updateUser(User user) {
        Map<String, Object> map = new HashMap<>();
        try {
            userService.updateUser(user);
            User user1 = userService.selectOne(user);
            map.put("status", "200");
            map.put("user", user1);
            return map;
        }catch (Exception e){
            map.put("status", "-200");
            map.put("message", "更新失败!");
            return map;
        }
    }



}











