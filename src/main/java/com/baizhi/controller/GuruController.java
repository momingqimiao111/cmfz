package com.baizhi.controller;

import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    private GuruService guruService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("showGuru")
    public Map<String, Object> showGuru(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        List<Guru> lists = null;
        Integer records = null;

        records = guruService.getRecords();
        lists = guruService.selectByPage((page - 1) * rows, rows);


        map.put("page", page);
        map.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        map.put("records", records);
        map.put("rows", lists);
        return map;
    }

    @RequestMapping("edit")
    public Map<String, String> editor(String oper, Guru guru, HttpServletRequest request) throws IOException {

        if ("add".equals(oper)) {
            guru.setId(UUID.randomUUID().toString());
            Map<String, String> map = guruService.addGuru(guru);
            return map;
        }
        if ("edit".equals(oper)) {
            Guru selectOne = guruService.selectOne(new Guru().setId(guru.getId()));
            guru.setPhoto(selectOne.getPhoto());
            Map<String, String> map = guruService.updateGuru(guru);
            return map;

        }
        return null;

    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile photo, HttpServletRequest request) throws IOException {
        System.out.println(!photo.getOriginalFilename().equals(""));
        //需要在submit之后进行一次图片路径的修改
        if (!photo.getOriginalFilename().equals("")) {
            Guru guru = new Guru();
            String realPath = request.getServletContext().getRealPath("/img");
            String suffix = "." + FilenameUtils.getExtension(photo.getOriginalFilename());
            String newName = UUID.randomUUID().toString() + suffix;
            photo.transferTo(new File(realPath, newName));
            String scheme = request.getScheme();
            String localhost = InetAddress.getLocalHost().toString().split("/")[1];
            int localPort = request.getLocalPort();
            String contextPath = request.getContextPath();
            guru.setId(id);
            guru.setPhoto(scheme + "://" + localhost + ":" + localPort + contextPath + "/img/" + newName);
            guruService.updateGuru(guru);
            System.out.println(guruService);
        }

    }
    @RequestMapping("selectAll")
    public List<Guru> selectAll() {
        return guruService.selectAll();
    }
    @RequestMapping("showAll")
    public Map showAll(String uid) {
        Map map = new HashMap();
        try {
            List<Guru> gurus = guruService.selectAll();
            map.put("status", "200");
            map.put("list", gurus);
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("status", "-200");
            map.put("message", "查询上师失败!");
            return map;

        }
    }

    public Map attentionGuru(String uid,String id) {
        Map map = new HashMap();
        try {
            SetOperations<String, String> stringStringSetOperations = stringRedisTemplate.opsForSet();
            stringStringSetOperations.add(uid, id);
            List<Guru> gurus = guruService.selectAll();
            map.put("list", gurus);
            map.put("status", "200");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("message", "关注上师失败!");
            map.put("status", "-200");
            return map;
        }
    }







}
