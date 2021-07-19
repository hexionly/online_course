package com.hsx.eduservice.controller;


import com.hsx.commonutils.R;
import com.hsx.eduservice.client.VodClient;
import com.hsx.eduservice.entity.EduChapter;
import com.hsx.eduservice.entity.EduVideo;
import com.hsx.eduservice.service.EduVideoService;
import com.hsx.servicebase.exception.HexionlyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author HEXIONLY
 * @since 2021-04-09
 */
@Api(tags = "课程小节")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public R addVideo(@ApiParam(name = "eduVideo", value = "小节信息", required = true)
                      @RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //删除小节时，里边的视频也要删除
    @ApiOperation(value = "删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@ApiParam(name = "id", value = "小节id", required = true)
                         @PathVariable String id) {
        //根据小节id获取视频id
        EduVideo eduVideo = eduVideoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //根据视频id远程调用删除视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            R result = vodClient.deleteAlyVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new HexionlyException(20001,"删除视频失败，熔断器。。");
            }
        }
        //先删视频再删小节
        eduVideoService.removeById(id);
        return R.ok();
    }
}

