package com.phoenixhell.serviceEdu.controller;

import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.phoenixhell.serviceEdu.entity.EduCourseDescription;
import com.phoenixhell.serviceEdu.entity.EduTeacher;
import com.phoenixhell.serviceEdu.service.EduCourseService;
import com.phoenixhell.serviceEdu.service.EduTeacherService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/serviceEdu/front/")
public class EduController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    @GetMapping("course/sortViewDesc")
    public CommonResult teacherSortByViewDesc(){
        List<EduCourse> courses = eduCourseService.query().orderByDesc("view_count").last("limit 8").list();
        List<EduTeacher> teachers = eduTeacherService.query().orderByDesc("level").orderByAsc("name").last("limit 1").list();
        Map<String,Object> map =new HashMap<>();
        map.put("courses",courses);
        map.put("teacher",teachers);
        return CommonResult.ok().emptyData().data(map);
    }
}
