import request from '@/utils/request'

export default {
    //生成订单
    createOrder(courseId) {
        return request({
            url: `/edu/order/createOrder/${courseId}`,
            method: 'post'
        })
    },
    //根据订单id查询订单信息
    getOrderInfo(orderId) {
        return request({
            url: `/edu/order/getOrderInfo/${orderId}`,
            method: 'get'
        })
    },
    //生成二维码
    createNative(orderNo) {
        return request({
            url: `/order/paylog/createNative/${orderNo}`,
            method: 'get'
        })
    },
    //查询订单支付状态
    queryPayStatus(orderNo) {
        return request({
            url: `/order/paylog/queryPayStatus/${orderNo}`,
            method: 'get'
        })
    },
}