package com.phoenixhell.serviceEdu.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.phoenixhell.serviceEdu.entity.EduSubject;
import com.phoenixhell.serviceEdu.entity.OneSubject;
import com.phoenixhell.serviceEdu.listener.EasyExcelReadListener;
import com.phoenixhell.serviceEdu.service.EduSubjectService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/serviceEdu/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("/addSubjectFromExcel")
    public CommonResult upload(MultipartFile file){
        try {
            System.out.println(eduSubjectService);
            eduSubjectService.readFromExcel(file);
        } catch (IOException e) {
            e.printStackTrace();
            return CommonResult.error().data("error",e.getMessage());
        }
        return CommonResult.ok();
    }
    @GetMapping("/getAllSubject")
    public CommonResult getAllSubject(){
        List<OneSubject> allSubject = eduSubjectService.getAllSubject();
        return CommonResult.ok().data("data",allSubject);
    }
}

