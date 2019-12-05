package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    @RequestMapping("showBanners")
    public Map<String, Object> showBanners(String searchField, String searchString, String searchOper, Integer page, Integer rows, Boolean _search) {
        Map<String, Object> map = new HashMap<>();
        List<Banner> lists = null;
        Integer records = null;
        if (_search) {
            records = bannerService.getRecords();
            lists = bannerService.selectByPage((page - 1) * rows, rows);
        } else {
            records = bannerService.getRecords();
            lists = bannerService.selectByPage((page - 1) * rows, rows);
        }

        map.put("page", page);
        map.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        map.put("records", records);
        map.put("rows", lists);
        return map;
    }

    @RequestMapping("edit")
    public Map<String,String> editor(String oper, Banner banner, HttpServletRequest request) throws IOException {
        System.out.println(banner);

        if ("add".equals(oper)) {
            banner.setId(UUID.randomUUID().toString()).setCreate_date(new Date());
            Map<String, String> map = bannerService.addBanner(banner);
            return map;
        }
        if ("edit".equals(oper)){
            Banner selectOne = bannerService.selectOne(new Banner().setId(banner.getId()));
            banner.setCover(selectOne.getCover()).setCreate_date(banner.getCreate_date());
            Map<String, String> map = bannerService.updateBanner(banner);
            return map;

        }
return null;

    }
    @RequestMapping("upload")
    public void upload(String id, MultipartFile cover,String oper,HttpServletRequest request) throws IOException {
        System.out.println(!cover.getOriginalFilename().equals(""));
        //需要在submit之后进行一次图片路径的修改
        if (!cover.getOriginalFilename().equals("")){
            if ("edit".equals(oper))deleteImg(id, request);
            Banner banner = new Banner();
            String realPath = request.getServletContext().getRealPath("/img");
            String suffix = "." + FilenameUtils.getExtension(cover.getOriginalFilename());
            String newName = UUID.randomUUID().toString() + suffix;
            cover.transferTo(new File(realPath, newName));
            String scheme = request.getScheme();
            String localhost = InetAddress.getLocalHost().toString().split("/")[1];
            int localPort = request.getLocalPort();
            String contextPath = request.getContextPath();
            banner.setId(id);
            banner.setCover(scheme+"://"+localhost+":"+localPort+contextPath+"/img/"+newName);
            bannerService.updateBanner(banner);
            System.out.println(banner);
        }

    }

    public void deleteImg(String id,HttpServletRequest request){
        Banner banner = bannerService.selectOne(new Banner().setId(id));
        String[] split = banner.getCover().split("/");
        String cover = split[split.length - 1];
        String realPath = request.getServletContext().getRealPath("/img");
        File file = new File(realPath, cover);
        if (file.exists()){
            file.delete();
        }
    }

}
