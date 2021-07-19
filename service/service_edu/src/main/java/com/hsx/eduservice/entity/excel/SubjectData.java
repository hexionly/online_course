package com.hsx.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {

    //一级目录
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    //二级目录
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
