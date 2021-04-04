package com.phoenixhell.serviceAcl.controller;

import com.phoenixhell.serviceAcl.service.IndexService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/serviceAcl/index")
public class indexController {
    @Autowired
    private IndexService indexService;
    @GetMapping("info")
    public CommonResult info() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map userInfo = indexService.getUserInfoByName(username);
        return CommonResult.ok().data("userInfo",userInfo);
    }
}
