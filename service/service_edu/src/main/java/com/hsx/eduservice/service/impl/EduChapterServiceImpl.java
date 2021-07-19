package com.hsx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsx.eduservice.entity.EduChapter;
import com.hsx.eduservice.entity.EduVideo;
import com.hsx.eduservice.entity.vo.ChapterVo;
import com.hsx.eduservice.entity.vo.VideoVo;
import com.hsx.eduservice.mapper.EduChapterMapper;
import com.hsx.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hsx.eduservice.service.EduVideoService;
import com.hsx.servicebase.exception.HexionlyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-09
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    /**
     * 课程大纲，根据课程id查询
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据id查询所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //根据课程id查询所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        //创建list，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();

        //遍历章节list集合封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();

            BeanUtils.copyProperties(eduChapter, chapterVo);
            //存入最终list
            finalList.add(chapterVo);

            //封装章节小节
            List<VideoVo> videoVoList = new ArrayList<>();

            //遍历小节list集合封装
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo = eduVideoList.get(j);
                //判断小节里的id是否和章节里的id一样
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
        }

        return finalList;
    }

    /**
     * 删除章节小节
     */
    @Override
    public boolean deleteChapter(String chapterId) {

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wrapper);
        //大于0不进行删除
        if (count > 0) {
            throw new HexionlyException(20001, "不能删除");
        } else {//进行删除
            return baseMapper.deleteById(chapterId) > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
