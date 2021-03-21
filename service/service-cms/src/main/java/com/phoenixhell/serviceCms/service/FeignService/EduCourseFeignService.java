package com.phoenixhell.serviceCms.service.FeignService;

import com.phoenixhell.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author phoenixhell
 * @since 2021/3/21 0021-上午 9:03
 */
@Component
@FeignClient(value = "server-edu")
public interface EduCourseFeignService {
    @GetMapping("/serviceEdu/front/course/sortViewDesc")
    public CommonResult teacherSortByViewDesc();
}
