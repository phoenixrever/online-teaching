package com.phoenixhell.serviceEdu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenixhell.serviceEdu.entity.EduTeacher;
import com.phoenixhell.serviceEdu.service.EduTeacherService;
import com.phoenixhell.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "前台讲师分页")
@RestController
@RequestMapping("/serviceEdu/front/teacher")
@CrossOrigin
public class FrontTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @ApiOperation(value = "物理分页")
    @GetMapping("/page/{currentPage}/{limit}")
    public CommonResult page(@PathVariable("currentPage") Integer page, @PathVariable("limit") Integer limit) {
        Page<EduTeacher> teacherPage = eduTeacherService.page(new Page<EduTeacher>(page, limit));
        Map<String, Object> map = new HashMap<>();
        map.put("total", teacherPage.getTotal());
        map.put("items", teacherPage.getRecords());
        map.put("current", teacherPage.getCurrent());
        map.put("totalPages", teacherPage.getPages());
        map.put("hasPrevious", teacherPage.hasPrevious());
        map.put("hasNext", teacherPage.hasNext());
        return CommonResult.ok().emptyData().data(map);
    }

}
