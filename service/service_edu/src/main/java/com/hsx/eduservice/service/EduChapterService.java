package com.hsx.eduservice.service;

import com.hsx.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hsx.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-09
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
