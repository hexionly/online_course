package com.hsx.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.hsx.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    //发送短信的方法
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }

        //这个是我的："LTAxxxxxxxGhN", "bDVekfa0puxxxxxxxx9b0e2y"
        //使用的这个是视频中老师的
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAxxxxxqJnk7", "9sxxxxxx632fRbG");
        IAcsClient client = new DefaultAcsClient(profile);

        try{
            //最终发送
            CommonResponse response = client.getCommonResponse(new CommonRequest() {{//设置相关固定参数
                setMethod(MethodType.POST);
                setDomain("dysmsapi.aliyuncs.com");
                setVersion("2017-05-25");
                setAction("sendSms");

                //发送相关的参数
                //手机号
                putQueryParameter("PhoneNumbers", phone);
                //申请的阿里云的签名名称
                putQueryParameter("SignName", "我的谷粒在线教育网站");
                //模板code
                putQueryParameter("TemplateCode", "SMS_18xxx135");
                //验证码值，转换成json值传递
                putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
            }});

            return response.getHttpResponse().isSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
