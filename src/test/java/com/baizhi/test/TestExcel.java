package com.baizhi.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baizhi.CmfzApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class TestExcel {
     String fileName = "E:\\excel\\学生表3.xlsx";
    @Test
    public void test1(){
        //EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
        ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
        excelWriter.write(data(), writeSheet);
        /// 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();

    }

    private static List<DemoData> data(){
        DemoData demoData = new DemoData("12", new Date(), 1.1, "123");
        DemoData demoData1 = new DemoData("12", new Date(), 1.1, "123");
        DemoData demoData2 = new DemoData("12", new Date(), 1.1, "123");
        DemoData demoData3 = new DemoData("12", new Date(), 1.1, "123");
        List<DemoData> demoData4 = Arrays.asList(demoData, demoData1, demoData2, demoData3);
        return demoData4;

    }
    @Test
    public void testRead(){
      //  EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

        //fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();

    }

}
