package com.phoenixhell.serviceVod.controller;

import com.phoenixhell.serviceVod.service.VideoUploadService;
import com.phoenixhell.serviceVod.util.ProgressBar;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/course/video")
public class VideoUploadController {
    @Autowired
    private VideoUploadService videoUploadService;
    @Autowired
    private ProgressBar progressBar;

    @PostMapping("/videoUpload")
    public CommonResult fileUpload(MultipartFile file){
        if(file==null){
            return CommonResult.error();
        }
        Map<String, String> map =null;
        try {
          map = videoUploadService.videoStreamUpload(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,Object>  objectMap=new HashMap<>();
        BeanUtils.copyProperties(map,objectMap);
        return  CommonResult.ok().emptyData().data(objectMap);
    }
    @GetMapping("/uploadProgress")
    public CommonResult uploadProgress(){
        HashMap<String, Object> objectHashMap = new HashMap<>();
        System.out.println("---------------------------");
        System.out.println(progressBar.getPercentMap().get("admin"));
        System.out.println("---------------------------");
        objectHashMap.put("admin",(Object) progressBar.getPercentMap().get("admin"));
        return CommonResult.ok().emptyData().data(objectHashMap);
    }
}
