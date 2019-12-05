package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    @Id
    private String id;
    private String phone;
    private String password;
    private String salt;
    private String status;
    private String photo;
    private String name;
    private String nick_name;
    private String sex;
    private String sign;//标签
    private String location;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date rigest_date;//注册时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date last_login;//最后登录

}















