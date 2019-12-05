package com.baizhi.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Id;
import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Guru implements Serializable {
    @Id
    @ExcelProperty("ID")
    private String id;
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("图片路径")
    private String photo;
    @ExcelProperty("状态")
    private String status;
    @ExcelProperty("法名")
    private String nick_name;

}





