package com.baizhi.service;

import com.baizhi.dao.CounterDao;
import com.baizhi.entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CounterServiceImpl implements CounterService {
    @Autowired
    private CounterDao counterDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Counter> selectAll(Counter counter) {
        return counterDao.select(counter);
    }

    @Override
    public List<Counter> addCounter(Counter counter) {
        counterDao.insertSelective(counter);
       return counterDao.select(new Counter().setUser_id(counter.getUser_id()).setCourse_id(counter.getCourse_id()));
    }

    @Override
    public List<Counter> updateCounter(Counter counter) {
        counterDao.updateByPrimaryKeySelective(counter);
       return counterDao.select(new Counter().setUser_id(counter.getUser_id()).setCourse_id(counter.getCourse_id()));
    }

    @Override
    public List<Counter> deleteCounter(Counter counter) {
        counterDao.deleteByPrimaryKey(counter);
        return counterDao.select(new Counter().setUser_id(counter.getUser_id()).setCourse_id(counter.getCourse_id()));
    }
}
