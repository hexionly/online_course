package com.hsx.oss.controller;

import com.hsx.commonutils.R;
import com.hsx.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "实现teacher头像上传")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    public R uploadOssFile(@ApiParam(name = "file", value = "上传文件流", required = true)
                                   MultipartFile file) {
        //上传头像文件 MultipartFile
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url", url);
    }
}
