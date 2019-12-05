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
public class Counter implements Serializable {
    @Id
    private String id;
    private String title;
    private Integer count;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_date;
    private String user_id;
    private String course_id;
}
