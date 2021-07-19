import request from '@/utils/request'

export default {
    //生成统计数据
    registerCount(day) {
        return request({
            url: `/statistics/daily/registerCount/${day}`,
            method: 'get'
        })
    },
    //获取统计数据
    showData(searchObj) {
        return request({
            url: `/statistics/daily/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get'
        })
    }
}
