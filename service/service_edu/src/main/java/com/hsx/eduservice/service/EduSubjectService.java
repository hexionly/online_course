package com.hsx.eduservice.service;

import com.hsx.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsx.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-08
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();
}
