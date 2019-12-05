package com.baizhi.service;

import com.baizhi.entity.Counter;

import java.util.List;

public interface CounterService {
    List<Counter> selectAll(Counter counter);
    List<Counter> addCounter(Counter counter);
    List<Counter> updateCounter(Counter counter);
    List<Counter> deleteCounter(Counter counter);
}
