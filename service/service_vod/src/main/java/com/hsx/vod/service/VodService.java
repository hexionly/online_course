package com.hsx.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    String uploadAliyunVideo(MultipartFile file);

    void removeMoreAlyVideo(List<String> videoIdList);
}
