package com.phoenixhell.serviceEdu.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.phoenixhell.serviceEdu.entity.EduChapter;
import com.phoenixhell.serviceEdu.entity.EduVideo;
import com.phoenixhell.serviceEdu.service.EduChapterService;
import com.phoenixhell.serviceEdu.service.EduVideoService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-03-10
 */
@RestController
@RequestMapping("/serviceEdu/eduChapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduVideoService eduVideoService;

    @GetMapping("/get/{id}")
    public CommonResult getAllChapter(@PathVariable("id") String id) {
        List<EduChapter> chapterList = eduChapterService.getChapterById(id);
        return CommonResult.ok().emptyData().data("chapterList", chapterList);
    }

    //    @PostMapping("/addChapter")
//    public CommonResult addChapter(@RequestBody EduChapter eduChapter){
//        boolean save = eduChapterService.save(eduChapter);
//        if(save){
//            return CommonResult.ok().emptyData().data("添加章节结果","添加章节成功");
//        }else{
//            return CommonResult.error().emptyData().data("添加章节结果","添加章节失败");
//        }
//    }
    @PostMapping("/addChapterVideos")
    public CommonResult addChapterWithVideos(@RequestBody Map<String, Object> chapterVideos) {
        if (chapterVideos.get("chapter") != null) {
            String chapterJson = JSONObject.toJSONString(chapterVideos.get("chapter"));
            EduChapter eduChapter = JSON.parseObject(chapterJson, new TypeReference<EduChapter>() {
            });
            boolean save = eduChapterService.save(eduChapter);
            if (chapterVideos.get("chapterVideos") != null && save) {
                String chapterVideosJson = JSONObject.toJSONString(chapterVideos.get("chapterVideos"));
                ArrayList<EduVideo> eduVideos = JSON.parseObject(chapterVideosJson, new TypeReference<ArrayList<EduVideo>>() {
                });
                eduVideos.forEach(e -> {
                    e.setChapterId(eduChapter.getId());
                    eduVideoService.save(e);
                });
            }
        }
        return CommonResult.ok();
    }
}

