package com.phoenixhell.oss.controller;

import com.phoenixhell.oss.service.OssService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author phoenixhell
 * @since 2021/3/5 0005-下午 2:11
 */
@RestController
//路徑多了個空格就是跨域
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping("/upload")
    public CommonResult uploadOss(MultipartFile file){
        String fileUrl = ossService.uploadFileToOss(file);
        return CommonResult.ok().emptyData().data("fileUrl",fileUrl);
    }
}

