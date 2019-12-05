package com.baizhi.dao;

import com.baizhi.entity.MapVO;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends Mapper<User> {

    Integer queryUserBySexAndDate(@Param("sex") String sex, @Param("date")Integer date);
    List<MapVO> queryCountLocation();
}
