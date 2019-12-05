package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    List<Article> selectByPage(Integer page, Integer rows);
    //获取总条数
    Integer getRecords();

    Map<String,String> addArticle(Article article);

    Map<String,String> updateArticle(Article article);

    Article selectOne(Article article);

    List<Article> queryByDate();

}
