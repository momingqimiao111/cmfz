package com.baizhi.controller;

import com.baizhi.entity.Course;
import com.baizhi.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    //查询当前用户所有功课
    @RequestMapping("showAll")
    public Map showAll(String uid) {
        Map map = new HashMap();
        try {
            List<Course> courses = courseService.selectAll();
            map.put("status", "200");
            map.put("option", courses);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "200");
            map.put("message", "查询功课失败!");
            return map;
        }

    }

    //添加功课
    @RequestMapping("addCourse")
    public Map addCourse(String uid,Course course) {
        Map map = new HashMap();
        try {
            course.setId(UUID.randomUUID().toString()).setCreate_date(new Date()).setUser_id(uid).setType("private");
            courseService.addCourse(course);
            List<Course> courses = courseService.selectAll();
            map.put("status", "200");
            map.put("option", courses);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "200");
            map.put("message", "添加功课失败!");
            return map;
        }

    }

    //删除功课

    @RequestMapping("deleteCourse")
    public Map deleteCourse(String uid,Course course) {
        Map map = new HashMap();
        try {

            courseService.deleteCourse(course);
            List<Course> courses = courseService.selectAll();
            map.put("status", "200");
            map.put("option", courses);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status", "200");
            map.put("message", "删除功课失败!");
            return map;
        }

    }








}
