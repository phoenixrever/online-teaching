package com.phoenixhell.serviceVod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;

/**
 * @author phoenixhell
 * @since 2021/3/16 0016-下午 4:24
 */

public class Vod {
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) {
        DefaultAcsClient client = null;
        try {
            String regionId = "cn-shanghai";  // 点播服务接入区域
            DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            client = new DefaultAcsClient(profile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
    /**
     * 获取视频上传地址和凭证
     * @param client 发送请求客户端
     * @return CreateUploadVideoResponse 获取视频上传地址和凭证响应数据
     * @throws Exception
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client,String title,String name) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle(title);
        request.setFileName(name);

        //UserData，用户自定义设置参数，用户需要单独回调URL及数据透传时设置(非必须)
        //JSONObject userData = new JSONObject();

        //UserData回调部分设置
        //JSONObject messageCallback = new JSONObject();
        //messageCallback.put("CallbackURL", "http://192.168.0.0/16");
        //messageCallback.put("CallbackType", "http");
        //userData.put("MessageCallback", messageCallback.toJSONString());

        //UserData透传数据部分设置
        //JSONObject extend = new JSONObject();
        //extend.put("MyId", "user-defined-id");
        //userData.put("Extend", extend.toJSONString());

        //request.setUserData(userData.toJSONString());

        return client.getAcsResponse(request);
    }
    /*获取播放地址函数*/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client,String videoId) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    /*获取播放凭证函数*/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client,String videoId) throws Exception {
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }
}
