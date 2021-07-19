package com.hsx.ucenterservice.controller;


import com.hsx.commonutils.JwtUtils;
import com.hsx.commonutils.R;
import com.hsx.commonutils.ordervo.UcenterMemberOrder;
import com.hsx.ucenterservice.entity.UcenterMember;
import com.hsx.ucenterservice.entity.vo.RegisterVo;
import com.hsx.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    //登录
    @PostMapping("login")
    public R login(@RequestBody UcenterMember ucenterMember) {
        //调用service方法
        //返回token值，jwt生成
        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token", token);
        /*
        eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.
        eyJzdWIiOiJndWxpLXVzZXIiLCJpYXQiOjE2MTkwMTY0MTYsImV4cCI6MTYxOTEwMjgxNiwiaWQiOiIxMjMxMzEyMzEyMzEiLCJuaWNrbmFtZSI6ImhleGkifQ.
        aRjCrh1U9xgE0RqJcnQASKSsQ77fHrylYqQpl62Rb10
         */
    }

    // 注册
    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMembereInfo")
    public R getMembereInfo(HttpServletRequest request){
        //使用jwt工具类方法，根据request获取头信息，返回id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        return R.ok().data("userInfo",ucenterMemberService.getById(memberId));
    }

    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        UcenterMemberOrder memberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,memberOrder);
        return memberOrder;
    }

    //查询某一天注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = ucenterMemberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }
}

