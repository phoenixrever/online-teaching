package com.phoenixhell.serviceEdu.controller;


import com.alibaba.fastjson.JSONObject;
import com.phoenixhell.serviceEdu.entity.EduVideo;
import com.phoenixhell.serviceEdu.service.EduChapterService;
import com.phoenixhell.serviceEdu.service.EduVideoService;
import com.phoenixhell.serviceEdu.vodClient.VodService;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private VodService vodService;

    @DeleteMapping("/delete/{id}")
    @Transactional
    public CommonResult deleteVideoById(@PathVariable("id") String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
//        EduVideo eduVideo = eduVideoService.query().eq("id", id).select("video_source_id").one();
        String videoSourceId = eduVideo.getVideoSourceId();
        boolean b = eduVideoService.removeById(id);
        if (videoSourceId != null) {
            CommonResult commonResult = vodService.deleteByVideoId(videoSourceId);
            System.out.println(commonResult);
            if (commonResult.getSuccess() == false){
                // 这里直接抛出异常促发事务回滚就好了熔断器异常处理方法其实没必要设置
                throw new MyException(20001,"hystrix callback");
            }
        }
        if (!b) {
            throw new MyException(20001, "事务错误");
        }
        return CommonResult.ok().data("删除结果", "删除成功");
    }

    @PutMapping("/edit")
    public CommonResult updateVideoById(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return CommonResult.ok().data("修改结果", "修改成功");
    }

    @PostMapping("/add")
    public CommonResult addVideoByChapterId(@RequestBody String eduVideos) {
        List<EduVideo> eduVideoList = JSONObject.parseArray(eduVideos, EduVideo.class);
        AtomicInteger addNumber = new AtomicInteger();
        eduVideoList.forEach(e -> {
            Integer count = eduChapterService.query().eq("id", e.getChapterId()).count();
            if (count > 0) {
                eduVideoService.save(e);
                addNumber.getAndIncrement();
            }
        });
        return CommonResult.ok().data("修改结果", addNumber.get() + "条记录");
    }
}

