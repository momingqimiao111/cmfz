package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.soap.SAAJResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Article> selectByPage(Integer page, Integer rows) {
        return articleDao.selectByRowBounds(new Article(),new RowBounds(page,rows));
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer getRecords() {
        return articleDao.selectCount(new Article());
    }

    @Override
    public Map<String, String> addArticle(Article article) {
        articleDao.insertSelective(article);
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId());

        return map;
    }

    @Override
    public Map<String, String> updateArticle(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId());
        return map;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Article selectOne(Article article) {
        return articleDao.selectOne(new Article().setId(article.getId()));
    }

    @Override
    public List<Article> queryByDate() {
        return articleDao.queryByDate();
    }
}
