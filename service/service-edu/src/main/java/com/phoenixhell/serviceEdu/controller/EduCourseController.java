package com.phoenixhell.serviceEdu.controller;


import com.phoenixhell.serviceEdu.entity.vo.Course;
import com.phoenixhell.serviceEdu.service.EduCourseService;
import com.phoenixhell.serviceEdu.service.impl.EduCourseServiceImpl;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
@RestController
@RequestMapping("/serviceEdu/eduCourse")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;
    @PostMapping("add")
    public CommonResult addCourse(@RequestBody Course course){
        String id = eduCourseService.saveCourse(course);
        if (id!=null) {
            return CommonResult.ok().emptyData().data("id", id);
        } else {
            return CommonResult.error().emptyData().data("添加结果", "添加失败");
        }
    }
}

