package com.phoenixhell.serviceEdu.controller;


import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.phoenixhell.serviceEdu.entity.vo.CompleteCourseInfo;
import com.phoenixhell.serviceEdu.entity.vo.Course;
import com.phoenixhell.serviceEdu.service.EduCourseService;
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
    public CommonResult addCourse(@RequestBody Course course) {
        String id = eduCourseService.saveCourse(course);
        if (id != null) {
            return CommonResult.ok().emptyData().data("id", id);
        } else {
            return CommonResult.error().emptyData().data("添加结果", "添加失败");
        }
    }

    @GetMapping("get/{id}")
    public CommonResult addCourse(@PathVariable("id") String id) {
        Course course = eduCourseService.getCourseById(id);
        if (course != null) {
            return CommonResult.ok().emptyData().data("courseInfo", course);
        } else {
            return CommonResult.error().emptyData().data("查询结果", "查询失败");
        }
    }

    @PutMapping("update")
    public CommonResult updateCourse(@RequestBody Course course) {
        Boolean aBoolean = eduCourseService.updateCourseById(course);
        if (aBoolean) {
            return CommonResult.ok().emptyData().data("修改结果", "修改成功");
        } else {
            return CommonResult.error().emptyData().data("修改结果", "修改失败");
        }
    }
    @GetMapping("/publish/{id}")
    public CommonResult publish(@PathVariable("id")String id){
        CompleteCourseInfo completeCourseInfo = eduCourseService.getCompleteCourseInfoById(id);
        return CommonResult.ok().emptyData().data("completeCourseInfo",completeCourseInfo);
    }
    @PutMapping("/publishCourse/{id}")
    public CommonResult publishCourse(@PathVariable("id")String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return CommonResult.ok().emptyData();
    }
}

