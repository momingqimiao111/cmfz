package com.baizhi.task;

import com.alibaba.excel.EasyExcel;
import com.baizhi.entity.Guru;
import com.baizhi.service.GuruService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Async
public class TestSpringTask {
    @Autowired
    private GuruService guruService;

    /**
     * 导出上师表的定时器
     */
    @Scheduled(cron = "0 0 10 ? *  Sun")
    public void task1(){
        List<Guru> gurus = guruService.selectAll();
        EasyExcel.write("E:\\excel\\"+new Date().getTime()+"上师信息表.xlsx",Guru.class).sheet().doWrite(gurus);
    }
}
