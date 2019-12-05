package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.hibernate.validator.constraints.EAN;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDao extends Mapper<Article> {

    List<Article> queryByDate();
}
