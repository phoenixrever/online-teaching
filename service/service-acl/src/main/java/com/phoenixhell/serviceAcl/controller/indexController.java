package com.phoenixhell.serviceAcl.controller;

import com.phoenixhell.serviceAcl.entity.AclPermission;
import com.phoenixhell.serviceAcl.entity.AclUser;
import com.phoenixhell.serviceAcl.entity.vo.MenuVo;
import com.phoenixhell.serviceAcl.mapper.TokenUserDetailsMapper;
import com.phoenixhell.serviceAcl.service.AclUserService;
import com.phoenixhell.serviceAcl.service.IndexService;
import com.phoenixhell.serviceAcl.utils.MenuHelper;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/serviceAcl/index")
public class indexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AclUserService aclUserService;
    @Autowired
    private TokenUserDetailsMapper tokenUserDetailsMapper;
    @GetMapping("/info")
    public CommonResult info() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map userInfo = indexService.getUserInfoByName(username);
        return CommonResult.ok().data("userInfo",userInfo);
    }
//    @GetMapping("/menu")
//    public CommonResult getMenuByUsername(){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        AclUser user = aclUserService.query().eq("username", username).one();
//        List<AclPermission> permissionList = indexService.getPermissionListByUserId(user.getId());
//        return CommonResult.ok().data("permissionList",permissionList);
//    }

    @GetMapping("/menu/{id}")
    public CommonResult getMenu(@PathVariable String id) {
        List<AclPermission> list = tokenUserDetailsMapper.getPermissionListByUserId(id);
        System.out.println(list);
        List<MenuVo> oneSubjectList = list.stream().
                filter(x -> x.getPid().equals(String.valueOf('0')))
                .map(y -> MenuHelper.menuVo(y))
                .collect(Collectors.toList());
        MenuHelper.getSubjectList(list, oneSubjectList);
        return CommonResult.ok().data("permissionList",oneSubjectList);
    }

}
