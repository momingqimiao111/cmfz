package com.baizhi.service;

import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class GuruServiceImpl implements GuruService {
    @Autowired
    private GuruDao guruDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Guru> selectByPage(Integer page, Integer rows) {
        return guruDao.selectByRowBounds(new Guru(),new RowBounds(page,rows));
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer getRecords() {
        return guruDao.selectCount(new Guru());
    }

    @Override
    public Map<String, String> addGuru(Guru guru) {
        guruDao.insertSelective(guru);
        Map<String, String> map = new HashMap<>();
        map.put("id", guru.getId());
        return map;
    }

    @Override
    public Map<String, String> updateGuru(Guru guru) {
        guruDao.updateByPrimaryKeySelective(guru);
        Map<String, String> map = new HashMap<>();
        map.put("id", guru.getId());
        return map;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Guru selectOne(Guru guru) {
        return guruDao.selectOne(new Guru().setId(guru.getId()));
    }

    @Override
    public List<Guru> selectAll() {
        return guruDao.selectAll();
    }

}
