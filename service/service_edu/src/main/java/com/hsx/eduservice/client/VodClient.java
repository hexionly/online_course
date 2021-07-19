package com.hsx.eduservice.client;

import com.hsx.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
//熔断器整合
@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    //视频删除
    //这里的参数使用@PathVariable("id")一定要有参数名称
    @DeleteMapping("/eduvod/video/deleteAlyVideo/{id}")
    R deleteAlyVideo(@PathVariable("id") String id);

    //同时删除多个视频
    @DeleteMapping("/eduvod/video/delete-batch")
    R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
