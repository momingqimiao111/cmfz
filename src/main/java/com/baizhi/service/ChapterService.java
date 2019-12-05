package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    List<Chapter> selectByPage(Integer page, Integer rows,String album_id);
    //获取总条数
    Integer getRecords();

    Map<String,String> addChapter(Chapter chapter);

    Map<String,String> updateChapter(Chapter chapter);

    Chapter selectOne(Chapter chapter);
}
