package com.phoenixhell.serviceStatistics.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author phoenixhell
 * @since 2021/3/31 0031-上午 10:49
 */
@FeignClient(value = "server-ucenter")
public interface FeignUcenterService {
    @GetMapping("/registerCount/{day}")
    public Integer registerCount(@PathVariable String day);
}
