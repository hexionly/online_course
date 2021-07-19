package com.hsx.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.hsx.oss.service.OssService;
import com.hsx.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {

        //工具类获取
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();

            //文件名称添加随机唯一值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");

            fileName = uuid+fileName;

            //把文件按日期进行分类
            String datePath = new DateTime().toString("yyyy/MM/dd");

            fileName = datePath+"/"+fileName;

            // 使用oss上传文件。
            /*
            第一参数，bucket名称
            第二参数，上传到oss的文件路径，文件名称，
            第三参数，上传文件输入流
             */
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后的文件路径返回
            //把上传到阿里云的路径手动拼接出来

            return "https://" + bucketName + "." + endpoint + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
