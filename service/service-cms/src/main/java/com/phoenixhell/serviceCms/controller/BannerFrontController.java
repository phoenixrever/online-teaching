package com.phoenixhell.serviceCms.controller;

import com.phoenixhell.serviceCms.service.FeignService.EduCourseFeignService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phoenixhell
 * @since 2021/3/20 0020-下午 3:02
 */
@RestController
@CrossOrigin
@RequestMapping("/serviceCms/bannerFront")
public class BannerFrontController {

    @Autowired
    private EduCourseFeignService eduCourseFeignService;
    @Cacheable(value = "homePage",key ="'teacherSortByViewDesc'")
    @GetMapping("/course/sortViewDesc")
    public CommonResult teacherSortByViewDesc(){
        return  eduCourseFeignService.teacherSortByViewDesc();
    }
}
