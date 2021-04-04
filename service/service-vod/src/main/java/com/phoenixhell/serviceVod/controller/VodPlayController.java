package com.phoenixhell.serviceVod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.phoenixhell.serviceVod.Config.VodServiceConfig;
import com.phoenixhell.serviceVod.util.Vod;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/serviceVod/video")
public class VodPlayController {
    @Autowired
    private VodServiceConfig vodServiceConfig;
    @GetMapping("/play/{videoId}")
    public CommonResult getPlayAuth(@PathVariable("videoId") String videoId){
        DefaultAcsClient defaultAcsClient = Vod.initVodClient(vodServiceConfig.getAccessKeyId(), vodServiceConfig.getAccessKeySecret());
        try {
            GetVideoPlayAuthResponse videoPlayAuth = Vod.getVideoPlayAuth(defaultAcsClient, videoId);
            String playAuth = videoPlayAuth.getPlayAuth();
            System.out.println(videoId);
            System.out.println(playAuth);
            GetPlayInfoResponse playInfo = Vod.getPlayInfo(defaultAcsClient, videoId);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = playInfo.getPlayInfoList();
            String playURL=playInfoList.get(0).getPlayURL();
            return CommonResult.ok().data("playAuth",playAuth).data("playURL",playURL);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            throw new MyException(20001,"获取播放地址失败");
        }
    }
}
