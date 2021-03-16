package com.phoenixhell.serviceVod.controller;

import com.phoenixhell.utils.CommonResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/course/video")
public class FIleUploadController {
    @PostMapping("/fileUpload")
    public CommonResult fileUpload(){
        return  CommonResult.ok();
    }
}
