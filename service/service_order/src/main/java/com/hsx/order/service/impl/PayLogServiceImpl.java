package com.hsx.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.hsx.order.entity.Order;
import com.hsx.order.entity.PayLog;
import com.hsx.order.mapper.PayLogMapper;
import com.hsx.order.service.OrderService;
import com.hsx.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsx.order.utils.HttpClient;
import com.hsx.servicebase.exception.HexionlyException;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-05-08
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    @Override
    public Map createNative(String orderNo) {
        try {
            //根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);

            //使用map设置生成二维码参数
            Map map = new HashMap<>();
            //1、设置支付参数
            map.put("appid", "wx74862e0dfcf69954");
            map.put("mch_id", "1558950191");
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", orderNo);
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            map.put("trade_type", "NATIVE");

            //发送HTTPclient请求，传送参数xml格式
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //执行请求发送
            client.post();

            //得到发送请求结果
            String xml = client.getContent();
            //将xml格式转换成map集合
            Map<String, String> xmlToMap = WXPayUtil.xmlToMap(xml);

            Map map1 = new HashMap();
            map1.put("out_trade_no", orderNo);
            map1.put("course_id", order.getCourseId());
            map1.put("total_fee", order.getTotalFee());
            map1.put("result_code", xmlToMap.get("result_code"));
            map1.put("code_url", xmlToMap.get("code_url"));

            //微信支付二维码2小时过期，可采取2小时未支付取消订单
            //redisTemplate.opsForValue().set(orderNo, map, 120, TimeUnit.MINUTES);
            return map1;

        } catch (Exception e) {
            throw new HexionlyException(20001, "支付二维码生成失败");
        }
    }

    //根据订单号查询订单支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3、返回第三方的数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map
            //7、返回
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

        //像支付表添加支付记录，更新的订单
        @Override
        public void updateOrderStatus (Map < String, String > map){
            //从map中获取订单号
            String orderNo = map.get("out_trade_no");
            //跟据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            Order order = orderService.getOne(wrapper);
            //更新订单表订单状态
            if(order.getStatus().intValue()==1){
                return;
            }
            order.setStatus(1);
            orderService.updateById(order);
            //向支付表中加记录
            PayLog payLog = new PayLog(){{
                setOrderNo(orderNo);
                setPayTime(new Date());
                setPayType(1);//1代表微信支付
                setTotalFee(order.getTotalFee());//支付金额
                setTradeState(map.get("trade_state"));//支付状态
                setTransactionId(map.get("transaction_id"));//订单流水号
                setAttr(JSONObject.toJSONString(map));
            }};
            baseMapper.insert(payLog);
        }
    }
