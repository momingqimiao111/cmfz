package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao chapterDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Chapter> selectByPage(Integer page, Integer rows,String album_id) {
        return chapterDao.selectByRowBounds(new Chapter().setAlbum_id(album_id),new RowBounds(page,rows));
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer getRecords() {
        return chapterDao.selectCount(new Chapter());
    }

    @Override
    public Map<String, String> addChapter(Chapter chapter) {
        chapterDao.insertSelective(chapter);
        Map<String, String> map = new HashMap<>();
        map.put("id", chapter.getId());
        return map;
    }

    @Override
    public Map<String, String> updateChapter(Chapter chapter) {
        chapterDao.updateByPrimaryKeySelective(chapter);
        Map<String, String> map = new HashMap<>();
        map.put("id", chapter.getId());
        return map;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Chapter selectOne(Chapter chapter) {
        return chapterDao.selectOne(chapter);
    }



}
