package com.phoenixhell.serviceEdu.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.phoenixhell.serviceEdu.entity.EduChapter;
import com.phoenixhell.serviceEdu.entity.EduVideo;
import com.phoenixhell.serviceEdu.service.EduChapterService;
import com.phoenixhell.serviceEdu.service.EduVideoService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
@RestController
@RequestMapping("/serviceEdu/eduVideo")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @DeleteMapping("/delete/{id}")
    public CommonResult deleteVideoById(@PathVariable("id") String id){
        boolean b = eduVideoService.removeById(id);
        if (b) {
            return CommonResult.ok().emptyData().data("删除结果", "删除成功");
        } else {
            return CommonResult.error().emptyData().data("删除结果", "删除失败");
        }
    }

    @PutMapping("/edit")
    public CommonResult updateVideoById(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return CommonResult.ok().emptyData().data("修改结果", "修改成功");
    }

    @PostMapping("/add")
    public CommonResult addVideoByChapterId(@RequestBody String eduVideos) {
        List<EduVideo> eduVideoList = JSONObject.parseArray(eduVideos, EduVideo.class);
        AtomicInteger addNumber= new AtomicInteger();
        eduVideoList.forEach(e-> {
            Integer count = eduChapterService.query().eq("id", e.getChapterId()).count();
            if (count > 0) {
                eduVideoService.save(e);
                addNumber.getAndIncrement();
            }
        });
      return CommonResult.ok().emptyData().data("修改结果", addNumber.get()+"条记录");
    }
}

