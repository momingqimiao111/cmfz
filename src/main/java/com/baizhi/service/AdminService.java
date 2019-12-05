package com.baizhi.service;

import com.baizhi.entity.Admin;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface AdminService {

    void login(Admin admin);
}
