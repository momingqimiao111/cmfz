package com.baizhi.service;

import com.baizhi.dao.CourseDao;
import com.baizhi.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDao courseDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Course> selectAll() {

        return courseDao.select(new Course());
    }

    @Override
    public List<Course> addCourse(Course course) {
        courseDao.insertSelective(course);

        return courseDao.select(new Course());
    }

    @Override
    public List<Course> deleteCourse(Course course) {
        courseDao.deleteByPrimaryKey(course);
        return courseDao.select(new Course());
    }
}
