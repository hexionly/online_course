package com.hsx.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {

    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔1高级讲师，2首席讲师")
    private Integer level;

    //这里使用的是String类型，前端传来的数据无需惊醒转换
    @ApiModelProperty(value = "查询开始时间", example = "2021-01-01 10:10:10")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2021-12-01 10:10:10")
    private String end;
}
