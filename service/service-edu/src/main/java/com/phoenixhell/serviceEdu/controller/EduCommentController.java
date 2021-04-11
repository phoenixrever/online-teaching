package com.phoenixhell.serviceEdu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.x.protobuf.MysqlxExpect;
import com.phoenixhell.serviceEdu.entity.EduComment;
import com.phoenixhell.serviceEdu.service.EduCommentService;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-27
 */
@RestController
@RequestMapping("/serviceEdu/eduComment")
public class EduCommentController {
    @Autowired
    private EduCommentService eduCommentService;
    @GetMapping("/{courseId}/{page}/{limit}")
    public CommonResult getCommentByCourseId(@PathVariable String courseId, @PathVariable Long page, @PathVariable Long limit){
        Page<EduComment> pageInfo = eduCommentService.page(new Page<EduComment>(page, limit), new QueryWrapper<EduComment>().eq("course_id", courseId));
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("items", pageInfo.getRecords());
        map.put("current", pageInfo.getCurrent());
        map.put("totalPages", pageInfo.getPages());
        map.put("hasPrevious", pageInfo.hasPrevious());
        map.put("hasNext", pageInfo.hasNext());
        return CommonResult.ok().data("pageInfo",map);
    }
    @PostMapping("/add")
    public CommonResult addComment(@RequestBody EduComment eduComment){
        boolean save = eduCommentService.save(eduComment);
        if(!save){
            throw  new MyException(20001,"添加评论失败");
        }
        return CommonResult.ok();
    }
}

