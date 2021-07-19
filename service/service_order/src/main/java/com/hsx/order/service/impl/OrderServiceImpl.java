package com.hsx.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsx.commonutils.ordervo.UcenterMemberOrder;
import com.hsx.eduservice.entity.frontvo.CourseWebVo;
import com.hsx.order.client.EduClient;
import com.hsx.order.client.UcenterClient;
import com.hsx.order.entity.Order;
import com.hsx.order.mapper.OrderMapper;
import com.hsx.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsx.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-05-08
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    //生成订单
    @Override
    public String createOrder(String courseId, String memberIdByJwtToken) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberIdByJwtToken);
        //通过远程调用根据课程id获取课程信息
        CourseWebVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建order对象，向order对象中设置需要的对象
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }

    //根据课程id和用户id查询你表中订单状态
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        return baseMapper.selectCount(wrapper) > 0;
    }
}
