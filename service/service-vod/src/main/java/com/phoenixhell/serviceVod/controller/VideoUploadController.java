package com.phoenixhell.serviceVod.controller;

import com.phoenixhell.serviceVod.service.VideoUploadService;
import com.phoenixhell.serviceVod.util.ProgressBar;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/serviceVod/video")
public class VideoUploadController {
    @Autowired
    private VideoUploadService videoUploadService;
//    @Autowired
//    private ProgressBar progressBar;

    @PostMapping("/videoUpload")
    public CommonResult fileUpload(MultipartFile file) throws ExecutionException, InterruptedException {
        if (file == null) {
            return CommonResult.error();
        }
        FutureTask<Map<String, Object>> mapFutureTask = new FutureTask<>(() -> {
            Map<String, Object> map = videoUploadService.videoStreamUpload(file);
            return map;
        });
        try {
            new Thread(mapFutureTask,"AAA").start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> map = mapFutureTask.get();
        System.out.println("-------------");
        System.out.println(map);
        return CommonResult.ok().data(map);
    }
    @DeleteMapping("/delete/{videoId}")
    public CommonResult deleteByVideoId(@PathVariable("videoId") String videoId){
        String videoId1 = videoUploadService.deleteByVideoId(videoId);
        return CommonResult.ok().data("videoId",videoId1);
    }

    @GetMapping("/uploadProgress")
    public CommonResult uploadProgress() {
        Map<String, Object> percentMap = ProgressBar.getPercentMap();
        System.out.println(percentMap + "****************");
        return CommonResult.ok().data(percentMap);
    }
}
