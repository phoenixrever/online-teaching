package com.phoenixhell.serviceEdu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenixhell.serviceEdu.entity.EduChapter;
import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.phoenixhell.serviceEdu.entity.EduVideo;
import com.phoenixhell.serviceEdu.entity.vo.CompleteCourseInfo;
import com.phoenixhell.serviceEdu.entity.vo.Course;
import com.phoenixhell.serviceEdu.service.EduChapterService;
import com.phoenixhell.serviceEdu.service.EduCourseDescriptionService;
import com.phoenixhell.serviceEdu.service.EduCourseService;
import com.phoenixhell.serviceEdu.service.EduVideoService;
import com.phoenixhell.serviceEdu.vodClient.VodService;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/serviceEdu/eduCourse")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private VodService vodService;
    @PostMapping("/add")
    public CommonResult addCourse(@RequestBody Course course) {
        String id = eduCourseService.saveCourse(course);
        if (id != null) {
            return CommonResult.ok().data("id", id);
        } else {
            return CommonResult.error().data("添加结果", "添加失败");
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult getCourse(@PathVariable("id") String id) {
        Course course = eduCourseService.getCourseById(id);
        if (course != null) {
            return CommonResult.ok().data("courseInfo", course);
        } else {
            return CommonResult.error().data("查询结果", "查询失败");
        }
    }

//    /**
//     * 没有必要 又烦又累 直接封装在类里面好了
//     * @param currentPage
//     * @param pageInfo
//     * @return
//     */
//    @PostMapping("/course/{currentPage}")
//    public CommonResult getCoursePagination(@PathVariable("currentPage") Long currentPage,
//                                            @RequestBody(required = false) Map<String, Object> pageInfo) {
//        QueryWrapper<CompleteCourseInfo> wrapper = new QueryWrapper<>();
//
//        if(!StringUtils.isEmpty(pageInfo.get("ltPrice"))){
//            String ltPrice = JSONObject.toJSONString(pageInfo.get("ltPrice"));
//            wrapper.lt(!StringUtils.isEmpty(ltPrice), "ec.price", new BigDecimal(ltPrice));
//        }
//        if(!StringUtils.isEmpty(pageInfo.get("gtPrice"))){
//            String gtPrice = JSONObject.toJSONString(pageInfo.get("gtPrice"));
//            wrapper.gt(!StringUtils.isEmpty(gtPrice), "ec.price", new BigDecimal(gtPrice));
//        }
//        Long limit=null;
//        if(!StringUtils.isEmpty(pageInfo.get("limit"))){
//            String limitStr = JSONObject.toJSONString(pageInfo.get("limit"));
//            limit = Long.parseLong(limitStr);
//        }else{
//            limit=10L;
//        }
//
//        if(!StringUtils.isEmpty(pageInfo.get("completeCourseInfo"))) {
//            String chapterJson = JSONObject.toJSONString(pageInfo.get("completeCourseInfo"));
//            CompleteCourseInfo completeCourseInfo = JSON.parseObject(chapterJson, new TypeReference<CompleteCourseInfo>() {
//            });
//            wrapper.like(!StringUtils.isEmpty(completeCourseInfo.getTitle()), "ec.title", completeCourseInfo.getTitle());
//            wrapper.like(!StringUtils.isEmpty(completeCourseInfo.getSubjectLevelOne()), "es.title", completeCourseInfo.getSubjectLevelOne());
//            wrapper.like(!StringUtils.isEmpty(completeCourseInfo.getSubjectLevelTwo()), "es2.title", completeCourseInfo.getSubjectLevelTwo());
//            wrapper.like(!StringUtils.isEmpty(completeCourseInfo.getTeacherName()), "et.name", completeCourseInfo.getTeacherName());
//        }
//        IPage<CompleteCourseInfo> completeCoursePage = eduCourseService.getCompleteCoursePage(new Page<>(currentPage, limit), wrapper);
//        return CommonResult.ok().data("list", completeCoursePage.getRecords());
//    }

    /**
     *
     * @param currentPage
     * @param
     * @return
     */
    @PostMapping("/{currentPage}")
    public CommonResult getCoursePagination(@PathVariable("currentPage") Long currentPage,
                                            @RequestBody() CompleteCourseInfo completeCourseInfo) {
        QueryWrapper<CompleteCourseInfo> wrapper = new QueryWrapper<>();
        wrapper.gt(!StringUtils.isEmpty(completeCourseInfo.getGtPrice()), "ec.price", completeCourseInfo.getGtPrice());
        wrapper.lt(!StringUtils.isEmpty(completeCourseInfo.getLtPrice()), "ec.price", completeCourseInfo.getLtPrice());
        Long limit = StringUtils.isEmpty(completeCourseInfo.getLimit())?10L:completeCourseInfo.getLimit();
        wrapper.like(!StringUtils.isEmpty(completeCourseInfo.getTitle()), "ec.title", completeCourseInfo.getTitle());
        wrapper.like(!StringUtils.isEmpty(completeCourseInfo.getSubjectLevelOne()), "es.title", completeCourseInfo.getSubjectLevelOne());
        wrapper.like(!StringUtils.isEmpty(completeCourseInfo.getSubjectLevelTwo()), "es2.title", completeCourseInfo.getSubjectLevelTwo());
        wrapper.like(!StringUtils.isEmpty(completeCourseInfo.getTeacherName()), "et.name", completeCourseInfo.getTeacherName());
        wrapper.eq(!StringUtils.isEmpty(completeCourseInfo.getStatus()), "ec.status",completeCourseInfo.getStatus());
        wrapper.orderByDesc("ec.gmt_create");
        wrapper.eq("ec.is_deleted",0);
        wrapper.eq("et.is_deleted",0);
        Page<CompleteCourseInfo> completeCoursePage = eduCourseService.getCompleteCoursePage(new Page<>(currentPage, limit), wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", completeCoursePage.getTotal());
        map.put("completeCourseInfo", completeCoursePage.getRecords());
        return CommonResult.ok().data(map); }

    @PutMapping("/update")
    public CommonResult updateCourse(@RequestBody Course course) {
        Boolean aBoolean = eduCourseService.updateCourseById(course);
        if (aBoolean) {
            return CommonResult.ok().data("修改结果", "修改成功");
        } else {
            return CommonResult.error().data("修改结果", "修改失败");
        }
    }

    @GetMapping("/publish/{id}")
    public CommonResult publish(@PathVariable("id") String id) {
        CompleteCourseInfo completeCourseInfo = eduCourseService.getCompleteCourseInfoById(id);
        return CommonResult.ok().data("completeCourseInfo", completeCourseInfo);
    }

    @PutMapping("/publishCourse/{id}")
    public CommonResult publishCourse(@PathVariable("id") String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return CommonResult.ok();
    }
    @DeleteMapping("/delete/{id}")
    public CommonResult deleteCourse(@PathVariable("id")String id){
        List<EduVideo> videos = eduVideoService.query().eq("course_id", id).list();
       //删除方法1
        videos.forEach(v->{
            if(StringUtils.isEmpty(v.getVideoSourceId())){
                vodService.deleteByVideoId(v.getVideoSourceId());
            }
            boolean removeVideo = eduVideoService.removeById(v.getId());
            if(!removeVideo){
                throw new MyException(20001,"删除小结失败事务回滚");
            }
        });
       //删除方法二
//        org.apache.commons.lang.StringUtils.join(list.toArray(), ",")
//        StringBuffer sb =new StringBuffer();
//        List<String> list = new ArrayList<>();
//        videos.forEach(v->{
//            sb.append(v.getVideoSourceId() + ",");
//            list.add(v.getId());
//        });
//        String deleteVideoIdStr=sb.substring(0,sb.length()-1);
//        if(StringUtils.isEmpty(deleteVideoIdStr)){
//            vodService.deleteByVideoId(deleteVideoIdStr);
//        }
//        boolean removeByIds = eduVideoService.removeByIds(list);
//        if(!removeByIds){
//            throw new MyException(20001,"删除小结失败事务回滚");
//        }

        boolean removeChapter = eduChapterService.remove(new QueryWrapper<EduChapter>().eq("course_id", id));
        boolean removeDescription =eduCourseDescriptionService.removeById(id);
        boolean removeEduCourse = eduCourseService.removeById(id);
        if(removeChapter && removeDescription && removeEduCourse){
            return CommonResult.ok();
        }else{
            throw new MyException(20001,"删除章节,描述,课程有失败事务回滚");
        }
    }
}

