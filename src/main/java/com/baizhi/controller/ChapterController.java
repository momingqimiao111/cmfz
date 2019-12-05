package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import com.baizhi.util.FileUploadUtil;
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
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;
    @RequestMapping("showChapter")
    public Map<String,Object> showChapter(Integer page, Integer rows,String album_id){
        Map<String, Object> map = new HashMap<>();
        List<Chapter> lists = chapterService.selectByPage((page - 1) * rows, rows,album_id);
        Integer records = chapterService.getRecords();
        map.put("page", page);
        map.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        map.put("records", records);
        map.put("rows", lists);
        return map;
    }
    @RequestMapping("edit")
    public Map<String,String> edit(String oper, Chapter chapter, HttpServletRequest request){
        if ("add".equals(oper)) {
            chapter.setId(UUID.randomUUID().toString()).setCreate_time(new Date());
            Album album = albumService.selectOne(new Album().setId(chapter.getAlbum_id()));
            albumService.updateAlbum(new Album().setId(album.getId()).setCount(album.getCount() + 1));
            Map<String, String> map = chapterService.addChapter(chapter);
            return map;
        }
        if("edit".equals(oper)){
            Chapter chapter1 = chapterService.selectOne(new Chapter().setId(chapter.getId()));

            chapter.setUrl(chapter1.getUrl());
            Map<String, String> map = chapterService.updateChapter(chapter);
            return map;

        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(String id, MultipartFile url, HttpServletRequest request) throws IOException {
        //需要在submit之后进行一次图片路径的修改
        if (!url.getOriginalFilename().equals("")){
            Chapter chapter = new Chapter();
            Long videoDuration = FileUploadUtil.getVideoDuration(url);
            chapter.setSize(String.valueOf(url.getSize()));
            chapter.setTime(videoDuration.toString());
            String realPath = request.getServletContext().getRealPath("/audio");
            String suffix = "." + FilenameUtils.getExtension(url.getOriginalFilename());
            String newName = UUID.randomUUID().toString() + suffix;
            url.transferTo(new File(realPath, newName));

            System.out.println(url.getSize()/1024/1024+"."+url.getSize()/1024%1024);
            System.out.println(videoDuration);
            String scheme = request.getScheme();
            String localhost = InetAddress.getLocalHost().toString().split("/")[1];
            int localPort = request.getLocalPort();
            String contextPath = request.getContextPath();
            chapter.setId(id);
            chapter.setUrl(scheme+"://"+localhost+":"+localPort+contextPath+"/audio    /"+newName);
            chapterService.updateChapter(chapter);



        }

    }

}
