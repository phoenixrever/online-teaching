package com.phoenixhell.serviceOrder.service;

import com.phoenixhell.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "server-edu")
public interface FeignCourseService {
    @GetMapping("/serviceEdu/eduCourse/get/{id}")
    public CommonResult getCourse(@PathVariable("id") String id);
}
