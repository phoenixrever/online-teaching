package com.phoenixhell.serviceVod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.phoenixhell.serviceVod.Config.VodServiceConfig;
import com.phoenixhell.serviceVod.util.Vod;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            Vod.getVideoPlayAuth(defaultAcsClient,videoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonResult.ok();
    }

}
