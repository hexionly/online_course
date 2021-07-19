package com.hsx.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsx.commonutils.JwtUtils;
import com.hsx.ucenterservice.utils.MD5;
import com.hsx.servicebase.exception.HexionlyException;
import com.hsx.ucenterservice.entity.UcenterMember;
import com.hsx.ucenterservice.entity.vo.RegisterVo;
import com.hsx.ucenterservice.mapper.UcenterMemberMapper;
import com.hsx.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-21
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //登录方法
    @Override
    public String login(UcenterMember ucenterMember) {
        //检测手机号和密码非空
        if (StringUtils.isEmpty(ucenterMember.getMobile()) ||
                StringUtils.isEmpty(ucenterMember.getPassword())) {
            throw new HexionlyException(20001, "登陆失败1");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", ucenterMember.getMobile());
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);

        if (mobileMember == null) {
            throw new HexionlyException(20001, "登陆失败2");
        }

        //判断密码
        //注册到数据库中的密码是加密的，把我们输入的密码先加密在比较
        //MD5加密，只能加密，不能解密
        if (!MD5.encrypt(ucenterMember.getPassword()).equals(mobileMember.getPassword())) {
            throw new HexionlyException(20001, "登陆失败3");
        }

        //判断用户是否被禁用
        if (mobileMember.getIsDisabled()) {
            throw new HexionlyException(20001, "登陆失败4");
        }

        //登陆成功，生成token
        return JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //验证非空
        if (StringUtils.isEmpty(registerVo.getMobile()) ||
                StringUtils.isEmpty(registerVo.getPassword()) ||
                StringUtils.isEmpty(registerVo.getCode()) ||
                StringUtils.isEmpty(registerVo.getNickname())) {
            throw new HexionlyException(20001, "登陆失败1");
        }

        //判断验证码
        //从redis中获取验证码
        String redisCode = redisTemplate.opsForValue().get(registerVo.getMobile());
        if(!registerVo.getCode().equals(redisCode)){
            throw new HexionlyException(20001, "验证码错误");
        }

        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",registerVo.getMobile());
        if(baseMapper.selectCount(wrapper)>0){
            throw new HexionlyException(20001, "改手机号已被注册");
        }

        baseMapper.insert(new UcenterMember(){{
            setMobile(registerVo.getMobile());
            setPassword(MD5.encrypt(registerVo.getPassword()));
            setNickname(registerVo.getNickname());
            setIsDisabled(false);
            setAvatar("https://hexionly-edu-admin.oss-cn-beijing.aliyuncs.com/2021/04/17/218b069fedcc4ba8a659a8bbad856cdbqc.jpg");
        }});
    }

    //微信扫码登录
    //根据openid查询
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        return baseMapper.selectOne(wrapper);
    }

    //查询某一天注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
