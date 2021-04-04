package com.phoenixhell.serviceEdu.controller.front;

import com.phoenixhell.serviceEdu.entity.EduCourse;
import com.phoenixhell.serviceEdu.entity.EduTeacher;
import com.phoenixhell.serviceEdu.service.EduCourseService;
import com.phoenixhell.serviceEdu.service.EduTeacherService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/serviceEdu/front/")
public class EduController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService eduCourseService;

    /**
     * @CachePut
     * <p>使用该注解标志的方法，每次都会执行，并将结果存入指定的缓存中。
     * 其他方法可以直接从响应的缓存中读取缓存数据，而不需要再去查询数据库。一般用在新增方法上。
     * cacheNames 缓存名，必填，它指定了你的缓存存放在哪块命名空间
     * key 可选属性，使用方法参数时我们可以直接使用“#参数名”或者“#p参数index”
     * @Cacheable(value="users", key="#user.id")
     *    public User find(User user) {
     *</p>
     *@CacheEvict
     *<p>使用该注解标志的方法，会清空指定的缓存。一般用在更新或者删除方法上
     * allEntries  是否清空所有缓存，默认为 false。如果指定为 true，则方法调用后将立即清空所有的缓存
     * beforeInvocation  是否在方法执行前就清空，默认为 false。如果指定为 true，则在方法执行前就会清空缓存
     *</p>
     */
//    根据方法对其返回结果进行缓存，下次请求时，如果缓存存在，
//    则直接读取缓存数据返回；如果缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上。
    @GetMapping("course/sortViewDesc")
//    @Cacheable(value = "banner",key = "'teacherSortByViewDesc'")
    public CommonResult teacherSortByViewDesc(){
        List<EduCourse> courses = eduCourseService.query().orderByDesc("view_count").last("limit 8").list();
        List<EduTeacher> teachers = eduTeacherService.query().orderByDesc("level").orderByAsc("name").last("limit 1").list();
        Map<String,Object> map =new HashMap<>();
        map.put("courses",courses);
        map.put("teacher",teachers);
        return CommonResult.ok().data(map);
    }
}
