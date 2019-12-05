package com.baizhi.service;

import com.baizhi.entity.MapVO;
import com.baizhi.entity.User;

import java.util.List;

public interface UserService {

    List<User> selectByPage(Integer page, Integer rows);
    //获取总条数
    Integer getRecords();

    void addUser(User user);

    void updateUser(User user);

    User selectOne(User user);

    List<User> selectAll();

    Integer queryUserBySexAndDate(String sex, Integer date);

    List<MapVO> queryCountLocation();

    User login(User user);


}
