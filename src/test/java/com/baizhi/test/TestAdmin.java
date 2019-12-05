package com.baizhi.test;

import com.baizhi.CmfzApplication;
import com.baizhi.dao.AdminDao;
import com.baizhi.dao.BannerDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.*;
import com.baizhi.service.AlbumService;
import com.baizhi.util.MD5Utils;
import it.sauronsoftware.jave.EncoderException;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.File;

import java.io.FileOutputStream;
import java.util.*;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class TestAdmin {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private ChapterDao chapterDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AlbumService albumService;

    @Test
    public void test1(){
        List<Admin> admins = adminDao.selectAll();
        admins.forEach(admin-> System.out.println(admin));
    }

    @Test
    public void test2(){
        Banner banner = bannerDao.selectOne(new Banner().setId("1"));
        System.out.println(banner);
    }
    @Test
    public void test3() throws EncoderException {
        List<Chapter> chapters = chapterDao.selectByRowBounds(new Chapter().setId("0b5543fa-095c-4cbd-b2c5-2f273119921e"), new RowBounds(0, 2));
        chapters.forEach(chapter -> System.out.println(chapter));

    }

    @Test
    public void test4(){
        User user = userDao.selectOne(new User().setPassword("123456").setPhone("123456"));
        System.out.println(user);
    }
    @Test
    public void test5(){
        System.out.println(MD5Utils.getCode());
    }

    @Test
    public void test6(){
        List<Album> albums = albumService.selectByDate();
        albums.forEach(album -> System.out.println(album));
    }



}
