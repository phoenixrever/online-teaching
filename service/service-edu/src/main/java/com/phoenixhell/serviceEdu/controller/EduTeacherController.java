package com.phoenixhell.serviceEdu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenixhell.serviceEdu.entity.EduTeacher;
import com.phoenixhell.serviceEdu.entity.vo.TeacherQuery;
import com.phoenixhell.serviceEdu.service.EduTeacherService;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-02-25
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/serviceEdu/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;


    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/listAll")
    public CommonResult teacherList() {
        return CommonResult.ok().data("teachers", eduTeacherService.list());
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/delete/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean remove = eduTeacherService.removeById(id);
        if (remove) {
            return CommonResult.ok().data("删除结果", "删除成功");
        } else {
            return CommonResult.error().data("删除结果", "删除失败");
        }
    }

    @ApiOperation(value = "物理分页")
    @GetMapping("/page/{currentPage}/{limit}")
    public CommonResult page(@PathVariable("currentPage") Integer page, @PathVariable("limit") Integer limit) {
        Page<EduTeacher> teacherPage = eduTeacherService.page(new Page<EduTeacher>(page, limit));
        Map<String, Object> map = new HashMap<>();
        map.put("total", teacherPage.getTotal());
        map.put("items", teacherPage.getRecords());
        return CommonResult.ok().data(map);
    }

    @ApiOperation(value = "带条件物理分页")
    @PostMapping("/pageCondition/{currentPage}/{limit}")
    public CommonResult pageCondition(@PathVariable("currentPage") Integer page,
                                      @PathVariable("limit") Integer limit,
                                      // 需要用post 提交 必须使用json传递数据
                                      @RequestBody(required = false) TeacherQuery teacherquery) {
                                     //TeacherQuery teacherquery){
        String name = teacherquery.getName();
        Integer level = teacherquery.getLevel();
        String begin = teacherquery.getBegin();
        String end = teacherquery.getEnd();
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_modified", end);
        }
        queryWrapper.orderBy(true, false, "gmt_modified");
        Page<EduTeacher> teacherPage = eduTeacherService.page(new Page<EduTeacher>(page, limit), queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", teacherPage.getTotal());
        map.put("items", teacherPage.getRecords());
        return CommonResult.ok().data(map);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("/addTeacher")
    public CommonResult addTeacher(
            @ApiParam(name = "teacher",value = "讲师对象",required = true) @RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return CommonResult.ok().data("添加结果", "添加成功");
        } else {
            return CommonResult.error().data("添加结果", "添加失败");
        }
    }

    @ApiOperation(value = "根据ID 查询讲师")
    @GetMapping("/getTeacher/{id}")
    public CommonResult getTeacher(@PathVariable("id") String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return CommonResult.ok().data("teacher", eduTeacher);
    }

    @ApiOperation(value = "根据ID 修改讲师")
    @PostMapping("/updateTeacher")
    public CommonResult updateTeacher(@RequestBody EduTeacher eduTeacher) {
        eduTeacher.setName(null);
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return CommonResult.ok().data("修改结果", "修改成功");
        } else {
            return CommonResult.error().data("修改结果", "修改失败");
        }
    }
    @ApiOperation(value = "根据name 查詢讲师")
    @GetMapping("/validateUniqueName/{name}")
    public CommonResult validateUniqueName(@PathVariable("name") String name) {
        EduTeacher eduTeacher = eduTeacherService.query().eq(true, "name", name).one();
        if (eduTeacher==null) {
            return CommonResult.ok().data("查詢结果", "查詢成功");
        } else {
            return CommonResult.error().data("查詢结果", "查詢失败");
        }
    }
    //视频里面的写法 实测 post 直接改成put 无任何问题
/*    @ApiOperation(value = "根据ID 修改讲师")
    @PutMapping("/updateTeacher/{id}")
    public CommonResult updateTeacher(@PathVariable("id")String id,@RequestBody EduTeacher eduTeacher) {
        eduTeacher.setId(id);
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return CommonResult.ok().data("修改结果", "修改成功");
        } else {
            return CommonResult.error().data("修改结果", "修改失败");
        }
    }*/
}

