package com.hsx.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsx.eduservice.entity.EduSubject;
import com.hsx.eduservice.entity.excel.SubjectData;
import com.hsx.eduservice.entity.subject.OneSubject;
import com.hsx.eduservice.entity.subject.TwoSubject;
import com.hsx.eduservice.listener.SubjectExcelListener;
import com.hsx.eduservice.mapper.EduSubjectMapper;
import com.hsx.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-08
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            //进行方法调取
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //课程分类列表
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询一级分类 parentID=0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //还有另外一这种写法
//        this.list(wrapperOne);

        //查询二级分类 parent!=0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于封装最终数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //封装一级分类
        //遍历查询的一级分类，获取每个分类值
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);

            OneSubject oneSubject = new OneSubject();

            //将一个对象中的值复制到另一个对象中
            BeanUtils.copyProperties(eduSubject, oneSubject);

            finalSubjectList.add(oneSubject);

            //遍历一级分类下的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject tSubject = twoSubjectList.get(j);

                //判断二级分类的parent ID和一级分类的id是否一样
                if (tSubject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级下边所有二级分类放到一级分类里边
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
