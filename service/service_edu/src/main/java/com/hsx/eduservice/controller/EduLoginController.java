package com.hsx.eduservice.controller;

import com.hsx.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "讲师登录")
@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin //解决跨域问题
public class EduLoginController {

    //讲师登录
    @ApiOperation(value = "讲师登录")
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }

    //显示讲师信息
    @ApiOperation(value = "讲师信息显示")
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
