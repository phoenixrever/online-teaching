package com.phoenixhell.serviceEdu.controller;


import com.phoenixhell.serviceEdu.entity.EduTeacher;
import com.phoenixhell.serviceEdu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
@Api(tags="讲师管理")
@RestController
@RequestMapping("/serviceEdu/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;


    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/listAll")
    public List<EduTeacher> teacherList() {
        return eduTeacherService.list();
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/delete/{id}")
    public Boolean deleteById(@PathVariable("id") String id) {
        return eduTeacherService.removeById(id);
    }
}

