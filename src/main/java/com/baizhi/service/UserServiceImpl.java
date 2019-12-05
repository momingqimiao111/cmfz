package com.baizhi.service;

import com.baizhi.annotation.MapAnnotation;
import com.baizhi.entity.MapVO;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Override
    public List<User> selectByPage(Integer page, Integer rows) {
        return userDao.selectByRowBounds(new User(), new RowBounds(page, rows));
    }

    @Override
    public Integer getRecords() {
        return userDao.selectCount(new User());
    }
    @MapAnnotation
    @Override
    public void addUser(User user) {
        userDao.insertSelective(user);
    }
    @MapAnnotation
    @Override
    public void updateUser(User user) {
        userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public User selectOne(User user) {
        return userDao.selectOne(user);
    }

    @Override
    public List<User> selectAll() {
        return userDao.select(new User());
    }

    @Override
    public Integer queryUserBySexAndDate(String sex, Integer date) {
        return userDao.queryUserBySexAndDate(sex,date);
    }

    @Override
    public List<MapVO> queryCountLocation() {
        return userDao.queryCountLocation();
    }

    @Override
    public User login(User user) {
        User user1 = userDao.selectOne(new User().setPhone(user.getPhone()));
        if (user1==null)
            throw new RuntimeException("手机号不存在");
        if (!user1.getPassword().equals(user.getPassword()))
            throw new RuntimeException("密码错误");
        return user1;
    }


}
