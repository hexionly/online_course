package com.hsx.ucenterservice.controller;

import com.google.gson.Gson;
import com.hsx.commonutils.JwtUtils;
import com.hsx.servicebase.exception.HexionlyException;
import com.hsx.ucenterservice.entity.UcenterMember;
import com.hsx.ucenterservice.service.UcenterMemberService;
import com.hsx.ucenterservice.utils.ConstantWxUtils;
import com.hsx.ucenterservice.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    //获取扫描人信息，添加数据
    @GetMapping("calback")
    public String callback(String code, String state) {
        try {
            //拿着code请求，微信固定的地址，得到两个值access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            //拼接三个参数，id，密钥，code
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );

            //返回拼接好的两个值，返回两个数
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessTokenInfo=" + accessTokenInfo);

            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");

            //添加到数据库
            //判断是否已注册过
            UcenterMember member = ucenterMemberService.getOpenIdMember(openid);
            if (member == null) {
                String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String.format(
                        userInfoUrl,
                        access_token,
                        openid
                );
                String userInfo = HttpClientUtils.get(userInfoUrl);

                //获取扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname");//昵称
                String headimgurl = (String) userInfoMap.get("headimgurl");//头像

                ucenterMemberService.save(new UcenterMember() {{
                    setOpenid(openid);
                    setNickname(nickname);
                    setAvatar(headimgurl);
                }});
            }

            //使用jwt生成token
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            return "redirect:http://localhost:3000?token="+jwtToken;
        } catch (Exception e) {
            throw new HexionlyException(20001,"微信登陆失败");
        }
    }

    //生成微信扫码的二维码
    @GetMapping("login")
    public String wxCode() {

        //固定地址，拼接参数
//        String url = "https://open.weixin.qq.com/connect/qrconnect?" +
//                "appId=" + ConstantWxUtils.WX_OPEN_APP_ID + "" +
//                "&response_type=code";

        String url = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //对redirect_url进行URLEncoding编码
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String hexionly = String.format(
                url,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "hexionly"
        );

        return "redirect:" + url;
    }
}
