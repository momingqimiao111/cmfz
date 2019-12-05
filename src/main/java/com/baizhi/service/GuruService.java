package com.baizhi.service;

import com.baizhi.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    List<Guru> selectByPage(Integer page, Integer rows);
    //获取总条数
    Integer getRecords();

    Map<String,String> addGuru(Guru guru);

    Map<String,String> updateGuru(Guru guru);

    Guru selectOne(Guru guru);

    List<Guru> selectAll();


}
