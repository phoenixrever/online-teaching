package com.phoenixhell.serviceVod.service.impl;

import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.phoenixhell.serviceVod.Config.VodServiceConfig;
import com.phoenixhell.serviceVod.service.VideoUploadService;
import com.phoenixhell.serviceVod.util.Vod;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class VideoUploadServiceImpl implements VideoUploadService {
    @Autowired
    private VodServiceConfig vodServiceConfig;

    @Override
    public Map<String, Object> videoStreamUpload(MultipartFile file) throws IOException {
        String title = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
        String fileName = file.getOriginalFilename();
        UploadStreamResponse response = Vod.UploadStreamVideo(vodServiceConfig.getAccessKeyId(), vodServiceConfig.getAccessKeySecret(), title, fileName, file.getInputStream());
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        Map<String, Object> map = new HashMap<>();
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            map.put("VideoId", response.getVideoId());
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            map.put("VideoId", response.getVideoId());
            map.put("ErrorCode", response.getCode());
            map.put("ErrorMessage", response.getMessage());
        }
        return map;
    }

    @Override
    public String deleteByVideoId(String videoIds) {
        DefaultAcsClient client = Vod.initVodClient(vodServiceConfig.getAccessKeyId(), vodServiceConfig.getAccessKeySecret());
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            response = Vod.deleteVideo(client, videoIds);
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            throw new MyException(20001,"ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
        return "RequestId = " + response.getRequestId();
    }

}
