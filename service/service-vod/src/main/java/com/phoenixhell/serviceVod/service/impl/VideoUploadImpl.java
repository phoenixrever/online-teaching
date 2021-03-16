package com.phoenixhell.serviceVod.service.impl;

import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.phoenixhell.serviceVod.Config.VodServiceConfig;
import com.phoenixhell.serviceVod.service.VideoUpload;
import com.phoenixhell.serviceVod.util.Vod;
import org.springframework.beans.factory.annotation.Autowired;

public class VideoUploadImpl implements VideoUpload {
    @Autowired
    private VodServiceConfig vodServiceConfig;
    @Override
    public String videoUpload() {
        String title="47-3.flv";
        String fileName="O:\\末名天N4梦子老师\\47\\47-3.flv";
        UploadVideoResponse response = Vod.uploadVideo(vodServiceConfig.getAccessKeyId(), vodServiceConfig.getAccessKeySecret(), title, fileName);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
        return null;
    }
}
