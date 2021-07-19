package com.hsx.videotest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import org.junit.Test;

public class VodTest {

    @Test
    public void test() throws ClientException {
        //根据视频id获取视频播放地址
        //初始化对象
        DefaultAcsClient client = InitObject.initVodClient("xxxxxxxxx", "xxxxxxxxxxx");

        //创建获取视频地址request和response
        GetPlayInfoRequest infoRequest = new GetPlayInfoRequest();
        GetPlayInfoResponse infoResponse = new GetPlayInfoResponse();

        //向request视频对象里设置id
        infoRequest.setVideoId("f23327c92ed1407db20d555f9b65c134");

        //调用初始化对象的方法传递request，获取数据
        infoResponse = client.getAcsResponse(infoRequest);

        System.out.println("playAuthor:"+infoResponse.getRequestId());

//        List<GetPlayInfoResponse.PlayInfo> playInfoList = infoResponse.getPlayInfoList();
//        //播放地址
//        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
//            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
//        }
//        //Base信息
//        System.out.print("VideoBase.Title = " + infoResponse.getVideoBase().getTitle() + "\n");
    }

    public static void main(String[] args) {

        String accessKeyId = "xxxxxxxxxxxxxxxxxxxxx";
        String accessKeySecret = "xxxxxxxxxxxxxxxxxx";
        String title = "6 - What If I Want to Move Faster - sdk upload11";
        String fileName ="H:/BaiduNetdiskDownload/6 - What If I Want to Move Faster.mp4";

        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}
