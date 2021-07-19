import request from '@/utils/request'

export default {
  //添加课时
  addVideo(eduVideo) {
    return request({
      url: `/eduservice/video/addVideo`,
      method: 'post',
      data:eduVideo
    })
  },
  //删除课时
  deleteVideo(id) {
    return request({
      url: `/eduservice/video/${id}`,
      method: 'delete'
    })
  },
  //删除课阿里云中视频
  deleteAlyVideo(id) {
    return request({
      url: `/eduvod/video/deleteAlyVideo/${id}`,
      method: 'delete'
    })
  },
}
