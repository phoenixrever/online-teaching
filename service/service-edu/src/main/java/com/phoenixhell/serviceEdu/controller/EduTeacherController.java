package com.phoenixhell.serviceEdu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenixhell.serviceEdu.entity.EduTeacher;
import com.phoenixhell.serviceEdu.entity.TeacherQuery;
import com.phoenixhell.serviceEdu.service.EduTeacherService;
import com.phoenixhell.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags="讲师管理")
@RestController
@RequestMapping("/serviceEdu/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;


    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/listAll")
    public CommonResult teacherList() {
        return CommonResult.ok().emptyData().data("teachers",eduTeacherService.list());
    }

    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/delete/{id}")
    public CommonResult deleteById(@PathVariable("id") String id) {
        boolean remove = eduTeacherService.removeById(id);
        if(remove){
            return CommonResult.ok().emptyData().data("删除结果","删除成功");
        }else{
            return CommonResult.error().emptyData().data("删除结果","删除失败");
        }
    }

    @ApiOperation(value = "物理分页")
    @GetMapping("/page/{currentPage}/{limit}")
    public CommonResult page(@PathVariable("currentPage")Integer page,@PathVariable("limit")Integer limit){
        Page<EduTeacher> teacherPage = eduTeacherService.page(new Page<EduTeacher>(page, limit));
        Map<String,Object> map=new HashMap<>();
        map.put("hasNext", teacherPage.hasNext());
        map.put("hasPrevious", teacherPage.hasPrevious());
        map.put("totals", teacherPage.getTotal());
        map.put("items", teacherPage.getRecords());
        return CommonResult.ok().emptyData().data(map);
    }

    @ApiOperation(value = "带条件物理分页")
    @PostMapping("/pageCondition/{currentPage}/{limit}")
    public CommonResult pageCondition(@PathVariable("currentPage")Integer page,
                                      @PathVariable("limit")Integer limit,
                                    // 需要用post 提交 必须使用json传递数据
                                    @RequestBody(required = false)  TeacherQuery teacherquery){
                                      //TeacherQuery teacherquery){
        String name = teacherquery.getName();
        Integer level = teacherquery.getLevel();
        String begin = teacherquery.getBegin();
        String end = teacherquery.getEnd();
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isEmpty(name)){
            queryWrapper.like("name",name);
        }
        if(StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if(StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }
        Page<EduTeacher> teacherPage = eduTeacherService.page(new Page<EduTeacher>(page, limit),queryWrapper);
        Map<String,Object> map=new HashMap<>();
        map.put("hasNext", teacherPage.hasNext());
        map.put("hasPrevious", teacherPage.hasPrevious());
        map.put("totals", teacherPage.getTotal());
        map.put("items", teacherPage.getRecords());
        return CommonResult.ok().emptyData().data(map);
    }
}

