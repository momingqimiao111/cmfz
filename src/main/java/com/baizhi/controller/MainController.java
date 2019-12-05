package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("main")
public class MainController {
    @Autowired
    private BannerService bannerService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ArticleService articleService;
    @RequestMapping("firstPage")
    public Map firstPage(String uid,String type,String sub_type) {
        Map map = new HashMap();
        if ("all".equals(type)){
            List<Banner> select = bannerService.select();
            List<Album> albums = albumService.selectByDate();
            List<Article> articles = articleService.queryByDate();
            map.put("status", 200);
            map.put("head", select);
            map.put("albums", albums);
            map.put("articles", articles);
            return map;
        }
        return null;
    }
}












