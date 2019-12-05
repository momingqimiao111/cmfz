package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerDao bannerDao;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Banner> selectByPage(Integer page, Integer rows) {
        return bannerDao.selectByRowBounds(new Banner(),new RowBounds(page,rows));
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer getRecords() {
        return bannerDao.selectCount(new Banner());
    }

    @Override
    public Map<String,String> addBanner(Banner banner) {
        bannerDao.insert(banner);
        Map<String, String> map = new HashMap<>();
        map.put("id", banner.getId());
        map.put("status","true");
        map.put("oper", "add");
        return map;
    }

    @Override
    public Map<String,String> updateBanner(Banner banner) {
        bannerDao.updateByPrimaryKeySelective(banner);
        Map<String, String> map = new HashMap<>();
        map.put("id", banner.getId());
        map.put("status","true");
        map.put("oper", "edit");
        return map;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Banner selectOne(Banner banner) {
        return bannerDao.selectOne(banner);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Banner> select() {
        return bannerDao.select(new Banner().setStatus("1"));
    }
}
