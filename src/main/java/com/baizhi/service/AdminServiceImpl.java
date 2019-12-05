package com.baizhi.service;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void login(Admin admin) {
        Admin admin1 = adminDao.selectOne(admin);
        if (admin1==null)
            throw new RuntimeException("用户名或密码错误！");
    }
}
