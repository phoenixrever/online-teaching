package com.phoenixhell.serviceVod;

import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
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
    public void testUpload() {
        String title="47-3.flv";
        String fileName="O:\\末名天N4梦子老师\\47\\47-3.flv";
        UploadVideoResponse response = Vod.uploadVideo(initConfigProperties.getAccessKeyId(), initConfigProperties.getAccessKeySecret(), title, fileName);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
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
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = Vod.getVideoPlayAuth(client,VIDEO_ID);
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
}
