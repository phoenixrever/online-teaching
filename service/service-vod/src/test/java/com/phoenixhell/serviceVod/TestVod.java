package com.phoenixhell.serviceVod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/3/16 0016-下午 3:53
 */
@SpringBootTest
public class TestVod {
    @Autowired
    private InitConfigProperties initConfigProperties;
    private final String VIDEO_ID="7f21772e8e164a63a4207e90c3a8a6e5";

    @Test
    public void testUpload() throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = Vod.initVodClient(initConfigProperties.getAccessKeyId(), initConfigProperties.getAccessKeySecret());
        CreateUploadVideoResponse response = new CreateUploadVideoResponse();
        try {
            response = Vod.createUploadVideo(client,"title","name.mp4");
            System.out.print("VideoId = " + response.getVideoId() + "\n");
            System.out.print("UploadAddress = " + response.getUploadAddress() + "\n");
            System.out.print("UploadAuth = " + response.getUploadAuth() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    @Test
    public void testPlayInfo() throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = Vod.initVodClient(initConfigProperties.getAccessKeyId(), initConfigProperties.getAccessKeySecret());
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = Vod.getPlayInfo(client,"7f21772e8e164a63a4207e90c3a8a6e5");
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");

    }
    @Test
    public void testPlayAuth() throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = Vod.initVodClient(initConfigProperties.getAccessKeyId(), initConfigProperties.getAccessKeySecret());
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            response = Vod.getPlayInfo(client,VIDEO_ID);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");

    }
}
