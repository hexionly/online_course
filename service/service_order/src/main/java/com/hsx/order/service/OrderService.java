package com.hsx.order.service;

import com.hsx.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-05-08
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberIdByJwtToken);

    boolean isBuyCourse(String courseId, String memberId);
}
