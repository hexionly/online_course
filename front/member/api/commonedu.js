import request from '@/utils/request'

export default {

  getPageList(page, limit, courseId) {
    return request({
      url: `/eduservice/comment/${page}/${limit}`,
      method: 'get',
      params: {courseId}
    })
  },
  addComment(eduComment) {
    return request({
      url: `/eduservice/comment/auth/save`,
      method: 'post',
      data: eduComment
    })
  }
}