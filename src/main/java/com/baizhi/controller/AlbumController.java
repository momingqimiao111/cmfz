package com.baizhi.controller;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterDao chapterDao;
    @RequestMapping("showAlbum")
    public Map<String,Object> showAlbum(Integer page,Integer rows){
        Map<String, Object> map = new HashMap<>();
        List<Album> lists = albumService.selectByPage((page - 1) * rows, rows);
        Integer records = albumService.getRecords();
        map.put("page", page);
        map.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        map.put("records", records);
        map.put("rows", lists);
        return map;
    }
    @RequestMapping("edit")
    public Map<String,String> edit(String oper, Album album, HttpServletRequest request){
        if ("add".equals(oper)) {
            album.setId(UUID.randomUUID().toString()).setCreate_date(new Date()).setCount(0);
            Map<String, String> map = albumService.addAlbum(album);
            return map;
        }
        if ("edit".equals(oper)){
            Album album1 = albumService.selectOne(new Album().setId(album.getId()));
            album.setCover(album1.getCover());
            Map<String, String> map = albumService.updateAlbum(album);
            return map;
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile cover, String oper, HttpServletRequest request) throws IOException {
        System.out.println(!cover.getOriginalFilename().equals(""));
        //需要在submit之后进行一次图片路径的修改
        if (!cover.getOriginalFilename().equals("")){
            if ("edit".equals(oper))deleteImg(id, request);
            Album album = new Album();
            String realPath = request.getServletContext().getRealPath("/img");
            String suffix = "." + FilenameUtils.getExtension(cover.getOriginalFilename());
            String newName = UUID.randomUUID().toString() + suffix;
            cover.transferTo(new File(realPath, newName));
            String scheme = request.getScheme();
            String localhost = InetAddress.getLocalHost().toString().split("/")[1];
            int localPort = request.getLocalPort();
            String contextPath = request.getContextPath();
            album.setId(id);
            album.setCover(scheme+"://"+localhost+":"+localPort+contextPath+"/img/"+newName);
            albumService.updateAlbum(album);

        }

    }

    public void deleteImg(String id,HttpServletRequest request){
        Album album = albumService.selectOne(new Album().setId(id));
        String[] split = album.getCover().split("/");
        String cover = split[split.length - 1];
        String realPath = request.getServletContext().getRealPath("/img");
        File file = new File(realPath, cover);
        if (file.exists()){
            file.delete();
        }
    }

    @RequestMapping("detail")
    public Map detail(String uid, String id) {
        HashMap map = new HashMap();
        try {

            Album album = albumService.selectOne(new Album().setId(id));
            System.out.println(id);
            List<Chapter> select = chapterDao.select(new Chapter().setAlbum_id(id));
            map.put("list", select);
            map.put("album", album);
            map.put("status", "200");
            return map;
        }catch (Exception e){
            e.printStackTrace();
            map.put("status", "-200");
            map.put("message", "详情查询失败!");
            return map;
        }


    }

}
