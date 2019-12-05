package com.baizhi.controller;

import com.baizhi.entity.Counter;
import com.baizhi.service.CounterService;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("counter")
public class CounterController {
    @Autowired
    private CounterService counterService;
    @RequestMapping("showAll")
    public Map showAll(String uid,String id) {
        Map map = new HashMap();
        try {
            Counter counter = new Counter();
            counter.setUser_id(uid).setCourse_id(id);
            List<Counter> counters = counterService.selectAll(counter);
            map.put("status", "200");
            map.put("counters", counters);
            return map;
        }catch (Exception e){
            map.put("status", "-200");
            map.put("message", "查询计时器失败!");
            return map;
        }

    }
    @RequestMapping("addCounter")
    public Map addCounter(String uid,Counter counter) {
        Map map = new HashMap();
        try {
            counter.setId(UUID.randomUUID().toString()).setUser_id(uid).setCount(0).setCreate_date(new Date());
            List<Counter> counters = counterService.addCounter(counter);
            map.put("status", "200");
            map.put("counters", counters);
            return map;
        }catch (Exception e){
            map.put("status", "-200");
            map.put("message", "添加计时器失败!");
            return map;
        }

    }
    @RequestMapping("updateCounter")
    public Map updateCounter(String uid,Counter counter) {
        Map map = new HashMap();
        try {
            counter.setUser_id(uid);
            List<Counter> counters = counterService.updateCounter(counter);
            map.put("status", "200");
            map.put("counters", counters);
            return map;
        }catch (Exception e){
            map.put("status", "-200");
            map.put("message", "查询计时器失败!");
            return map;
        }

    }
    @RequestMapping("deleteCounter")
    public Map deleteCounter(String uid,Counter counter) {
        Map map = new HashMap();
        try {
            counter.setUser_id(uid);
            List<Counter> counters = counterService.deleteCounter(counter);
            map.put("status", "200");
            map.put("counters", counters);
            return map;
        }catch (Exception e){
            map.put("status", "-200");
            map.put("message", "查询计时器失败!");
            return map;
        }

    }


}
