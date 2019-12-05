package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    List<Album> selectByPage(Integer page, Integer rows);
    //获取总条数
    Integer getRecords();

    Map<String,String> addAlbum(Album album);

    Map<String,String> updateAlbum(Album album);

    Album selectOne(Album album);

    List<Album> selectByDate();
}
