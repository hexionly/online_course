package com.hsx.msmservice.controller;

import com.hsx.commonutils.R;
import com.hsx.msmservice.service.MsmService;
import com.hsx.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmApiController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信的方法
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //从redis中获取验证码，取到直接返回
        String code = redisTemplate.opsForValue().get(phone);

        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }

        //redis没有再从阿里云发送
        //生成随机值，传递阿里云进行发送
        //获取四位验证码
        code = RandomUtil.getFourBitRandom();

        System.out.println("验证码为："+code);

        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用阿里发送短信
        boolean isSend = msmService.send(param, phone);

        if (isSend) {
            //设置redis缓存时间位五分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }
        return R.error().message("短信发送失败");
    }
}
