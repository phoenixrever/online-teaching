package com.phoenixhell.serviceAcl.controller;


import com.phoenixhell.security.entity.SecurityUser;
import com.phoenixhell.serviceAcl.entity.AclUser;
import com.phoenixhell.serviceAcl.service.IndexService;
import com.phoenixhell.utils.CommonResult;
import org.assertj.core.data.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author phoeixhell
 * @since 2021-03-31
 */
@RestController
@RequestMapping("/serviceAcl/user")
public class AclUserController {
    @Autowired
    private IndexService indexService;
    @GetMapping("info")
    public CommonResult info() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map userInfo = indexService.getUserInfoByName(username);
        return CommonResult.ok().data("userInfo",userInfo);
    }
}

