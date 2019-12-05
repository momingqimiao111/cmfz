package com.baizhi.test;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HIAPAD on 2019/12/2.
 */
public class DemoDataListener extends AnalysisEventListener<DemoData>{
    List list = new ArrayList();
    // 每行数据读取完成后会调用invoke方法
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println(data);
        list.add(data);
    }
    // 全部读取后执行 doAfterAllAnalysed 方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("over");
        // save(list)
    }
}
