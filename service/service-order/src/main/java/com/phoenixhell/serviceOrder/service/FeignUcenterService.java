package com.phoenixhell.serviceOrder.service;

import com.phoenixhell.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Component
@FeignClient("server-ucenter")
public interface FeignUcenterService {
    @GetMapping("/serviceUcenter/member/feignInfo/{userId}")
    public CommonResult getUserInfoByTokenId(@PathVariable String userId);
}
