package com.hsx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsx.eduservice.entity.EduTeacher;
import com.hsx.eduservice.mapper.EduTeacherMapper;
import com.hsx.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //查询首页名师
    @Cacheable(value = "teacher", key = "'selectTeacherList'")
    @Override
    public List<EduTeacher> teacherList() {
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        return baseMapper.selectList(teacherWrapper);
    }

    //分页查询讲师方法
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(pageTeacher, wrapper);

        return new HashMap<String, Object>() {{
            put("records", pageTeacher.getRecords());
            put("current", pageTeacher.getCurrent());
            put("pages", pageTeacher.getPages());
            put("total", pageTeacher.getTotal());
            put("size", pageTeacher.getSize());
            put("hasNext", pageTeacher.hasNext());
            put("hasPrevious", pageTeacher.hasPrevious());
        }};
    }
}
