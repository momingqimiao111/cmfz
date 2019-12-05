package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    List<Banner> selectByPage(Integer page, Integer rows);
    //获取总条数
    Integer getRecords();

    Map<String,String> addBanner(Banner banner);

    Map<String,String> updateBanner(Banner banner);

    Banner selectOne(Banner banner);

    List<Banner> select();
}
