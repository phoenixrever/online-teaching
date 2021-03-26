package com.phoenixhell.serviceEdu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenixhell.serviceEdu.entity.EduChapter;
import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.phoenixhell.serviceEdu.entity.EduTeacher;
import com.phoenixhell.serviceEdu.entity.OneSubject;
import com.phoenixhell.serviceEdu.entity.vo.CompleteCourseInfo;
import com.phoenixhell.serviceEdu.service.EduChapterService;
import com.phoenixhell.serviceEdu.service.EduCourseService;
import com.phoenixhell.serviceEdu.service.EduSubjectService;
import com.phoenixhell.serviceEdu.service.EduTeacherService;
import com.phoenixhell.utils.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "前台讲师分页")
@RestController
@RequestMapping("/serviceEdu/front/teacher")
@CrossOrigin
public class FrontTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;
    @Autowired
    private EduSubjectService eduSubjectService;
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

    @GetMapping("/course/{id}")
    public CommonResult getCourseByTeacherId(@PathVariable("id")String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        List<EduCourse> eduCourses = eduCourseService.query().eq("teacher_id", id).list();
        return CommonResult.ok().emptyData().data("teacher",eduTeacher).data("course",eduCourses);
    }

    @GetMapping("/completeCourse/{id}")
    public CommonResult publish(@PathVariable("id") String id) {
        CompleteCourseInfo completeCourseInfo = eduCourseService.getCompleteCourseInfoById(id);
        List<EduChapter> chapter = eduChapterService.getChapterById(id);
        return CommonResult.ok().emptyData().data("completeCourseInfo", completeCourseInfo).data("chapterList",chapter);
    }

    @PostMapping("/search/{currentPage}")
    public CommonResult getCoursePagination(@PathVariable("currentPage") Long currentPage,
                                            @RequestBody() CompleteCourseInfo completeCourseInfo) {
        List<OneSubject> allSubject = eduSubjectService.getAllSubject();
        QueryWrapper<CompleteCourseInfo> wrapper = new QueryWrapper<>();
        Long limit = StringUtils.isEmpty(completeCourseInfo.getLimit())?10L:completeCourseInfo.getLimit();
        wrapper.eq(!StringUtils.isEmpty(completeCourseInfo.getSubjectLevelOne()), "es.title", completeCourseInfo.getSubjectLevelOne());
        wrapper.like(!StringUtils.isEmpty(completeCourseInfo.getSubjectLevelTwo()), "es2.title", completeCourseInfo.getSubjectLevelTwo());


        Page<CompleteCourseInfo> completeCoursePage = eduCourseService.getCompleteCoursePage(new Page<>(currentPage, limit), wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("total", completeCoursePage.getTotal());
        map.put("completeCourseInfo", completeCoursePage.getRecords());
        map.put("current", completeCoursePage.getCurrent());
        map.put("totalPages", completeCoursePage.getPages());
        map.put("hasPrevious", completeCoursePage.hasPrevious());
        map.put("hasNext", completeCoursePage.hasNext());
        map.put("allSubject", allSubject);
        return CommonResult.ok().emptyData().data(map); }
}
