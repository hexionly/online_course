package com.hsx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsx.eduservice.client.VodClient;
import com.hsx.eduservice.entity.EduChapter;
import com.hsx.eduservice.entity.EduVideo;
import com.hsx.eduservice.mapper.EduVideoMapper;
import com.hsx.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-09
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //根据课程id删除小节
    //删除小节同时删除对应视频的文件
    @Override
    public void removeVideoByCourseId(String courseId) {
        //根据视频id删除所有的视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        //查询指定id
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapperVideo);

        //将对象中的videoid转换成string类型
        List<String> videoListIds = new ArrayList<>();
        for (int i = 0; i < eduVideos.size(); i++) {
            if (!StringUtils.isEmpty(eduVideos.get(i).getVideoSourceId())) {
                videoListIds.add(eduVideos.get(i).getVideoSourceId());
            }
        }
        if (videoListIds.size() > 0) {
            vodClient.deleteBatch(videoListIds);
        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
