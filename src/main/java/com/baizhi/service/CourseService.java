package com.baizhi.service;

import com.baizhi.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> selectAll();

    List<Course> addCourse(Course course);

    List<Course> deleteCourse(Course course);

}
