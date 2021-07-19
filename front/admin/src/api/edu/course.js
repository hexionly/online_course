import request from '@/utils/request'
export default {
    //1 添加课程信息 1
    addCourseInfo(courseInfo) {
        return request({
            url: '/eduservice/course/addCourseInfo',
            method: 'post',
            data: courseInfo
        })
    },
    //2 查询所有讲师 1
    getListTeacher() {
        return request({
            url: '/eduservice/teacher/findAll',
            method: 'get'
        })
    },
    //根据课程id查询课程基本信息 1
    getCourseInfoId(id) {
        return request({
            url: '/eduservice/course/getCourseInfo/' + id,
            method: 'get'
        })
    },
    //修改课程信息 1
    updateCourseInfo(courseInfo) {
        return request({
            url: '/eduservice/course/updateCourseInfo',
            method: 'post',
            data: courseInfo
        })
    },
    //课程确认信息显示 1
    getPublihCourseInfo(id) {
        return request({
            url: '/eduservice/course/getPublishCourseInfo/' + id,
            method: 'get'
        })
    },
    //课程最终发布 1
    publihCourse(id) {
        return request({
            url: '/eduservice/course/publishCourse/' + id,
            method: 'post'
        })
    },
    //课程列表 1
    publishCourseList(page, limit, courseQuery) {
        return request({
            url: `/eduservice/course/pageCourse/${page}/${limit}`,
            method: 'post',
            data: courseQuery
        })
    },
    //删除课程
    deleteCourse(courseId) {
        return request({
            url: `/eduservice/course/deleteCourse/${courseId}`,
            method: 'delete'
        })
    }

}
