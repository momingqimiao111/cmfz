package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Album implements Serializable {
    @Id
    @KeySql(sql ="select uuid()",order = ORDER.BEFORE)
    private String id;
    private String title;
    private Integer score;
    private String author;
    private String broadcast;
    private Integer count;
    private String description;
    private String status;
    private Date create_date;
    private String cover;
}
