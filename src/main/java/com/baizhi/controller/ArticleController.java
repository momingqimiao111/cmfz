package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import com.baizhi.service.ArticleService;
import com.baizhi.service.GuruService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private GuruService guruService;

    @RequestMapping("showArticle")
    public Map<String, Object> showArticle(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        List<Article> lists = null;
        Integer records = null;

        records = articleService.getRecords();
        lists = articleService.selectByPage((page - 1) * rows, rows);


        map.put("page", page);
        map.put("total", records % rows == 0 ? records / rows : records / rows + 1);
        map.put("records", records);
        map.put("rows", lists);
        return map;
    }
    @RequestMapping("edit")
    public void addArticle(Article article){

        if (article.getId().equals("")){
            Guru guru = guruService.selectOne(new Guru().setId(article.getGuru_id()));
            article.setId(UUID.randomUUID().toString()).setImg(guru.getPhoto()).setCreate_date(new Date()).setPublish_date(new Date());
            articleService.addArticle(article);
        }else {
            Article article1 = articleService.selectOne(new Article().setId(article.getId()));
            article1.setTitle(article.getTitle()).setContent(article.getContent()).setGuru_id(article.getGuru_id());
            articleService.updateArticle(article1);System.out.println(article1);
        }

    }
    @RequestMapping("detail")
    public Map detail(String uid, String id) {
        HashMap map = new HashMap();
        try {
            Article article = articleService.selectOne(new Article().setId(id));
            map.put("status", "200");
            map.put("article", article);
            return map;
        }catch (Exception e){
            map.put("status", "-200");
            map.put("message", "详情查询失败!");
            return map;
        }


    }



}
