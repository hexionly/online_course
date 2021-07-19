package com.hsx.order.controller;


import com.hsx.commonutils.R;
import com.hsx.order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-05-08
 */
@RestController
@RequestMapping("/order/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //生成微信支付二维码
    //参数订单号
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回相关信息，包括二维码地址，和其他的一些信息
        Map map = payLogService.createNative(orderNo);
        System.out.println("====map===="+map);
        return R.ok().data(map);
    }
    
    //查询订单支付状态
    //参数：订单号，根据订单号查询支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if(map==null){
            return R.error().message("支付出错");
        }
        //如果不为空，返回订单状态
        //支付成功
        if (map.get("trade_state").equals("SUCCESS")){
            //向支付表中加记录，更新订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().message("支付中...");
    }
}

