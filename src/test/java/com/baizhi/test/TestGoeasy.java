package com.baizhi.test;

import com.baizhi.CmfzApplication;
import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(SpringRunner.class)
public class TestGoeasy {
    @Test
    public void test(){
        // 查询数据 list
        // 把list集合转换为 json字符串
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-7678885b4d8443f6abe98b0fe45edaf2");
        goEasy.publish("cmfz", "Hello World!");//content : json字符串
        goEasy.publish("cmfz","Hello, GoEasy!", new PublishListener(){
            @Override
            public void onSuccess() {
                System.out.print("Publish message success.");
            }
            @Override
            public void onFailed(GoEasyError error) {
                System.out.print("Publish message failed, Error code:" + error.getCode() + " Error message: " + error.getContent());
            }
        });
    }

}
