package com.baizhi.service;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Album> selectByPage(Integer page, Integer rows) {
        return albumDao.selectByRowBounds(new Album(), new RowBounds(page, rows));
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer getRecords() {
        return albumDao.selectCount(new Album());
    }

    @Override
    public Map<String, String> addAlbum(Album album) {
        albumDao.insert(album);
        Map<String, String> map = new HashMap<>();
        map.put("id", album.getId());
        map.put("status", "true");
        map.put("oper", "add");
        return map;
    }

    @Override
    public Map<String, String> updateAlbum(Album album) {
        albumDao.updateByPrimaryKeySelective(album);
        Map<String, String> map = new HashMap<>();
        map.put("id", album.getId());
        map.put("status", "true");
        map.put("oper", "edit");
        return map;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Album selectOne(Album album) {
        return albumDao.selectOne(album);
    }

    @Override
    public List<Album> selectByDate() {
        Example example = new Example(Album.class);
        example.setOrderByClause("create_date desc");
        return albumDao.selectByExampleAndRowBounds(example,new RowBounds(0,6));
    }
}
