package com.phoenixhell.serviceAcl.controller;

import com.phoenixhell.serviceAcl.entity.AclPermission;
import com.phoenixhell.serviceAcl.entity.AclUser;
import com.phoenixhell.serviceAcl.entity.vo.MenuVo;
import com.phoenixhell.serviceAcl.mapper.TokenUserDetailsMapper;
import com.phoenixhell.serviceAcl.service.AclPermissionService;
import com.phoenixhell.serviceAcl.service.AclUserService;
import com.phoenixhell.serviceAcl.service.IndexService;
import com.phoenixhell.serviceAcl.utils.MenuHelper;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private AclPermissionService aclPermissionService;

    @GetMapping("/info")
    public CommonResult info() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map userInfo = indexService.getUserInfoByName(username);
        return CommonResult.ok().data("userInfo", userInfo);
    }
//    @GetMapping("/menu")
//    public CommonResult getMenuByUsername(){
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        AclUser user = aclUserService.query().eq("username", username).one();
//        List<AclPermission> permissionList = indexService.getPermissionListByUserId(user.getId());
//        return CommonResult.ok().data("permissionList",permissionList);
//    }

    @GetMapping("/menu")
    public CommonResult getMenu() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map userInfo = indexService.getUserInfoByName(username);
        String id = (String) userInfo.get("id");
        List<AclPermission> list = tokenUserDetailsMapper.getPermissionListByUserId(id);
        System.out.println(list);
        List<MenuVo> oneSubjectList = list.stream().
                filter(x -> x.getPid().equals(String.valueOf('0')))
                .map(y -> MenuHelper.menuVo(y))
                .collect(Collectors.toList());
        MenuHelper.getSubjectList(list, oneSubjectList);
        return CommonResult.ok().data("permissionList", oneSubjectList);
    }

    @PostMapping("/menu/add")
    @Transactional
    public CommonResult addMenu(@RequestBody Map<String, MenuVo> map) {
        System.out.println(map);
        AclPermission permissionOne = new AclPermission();
        AclPermission permissionTwo = new AclPermission();
        BeanUtils.copyProperties(map.get("one"), permissionOne);
        BeanUtils.copyProperties(map.get("two"), permissionTwo);
        permissionOne.setPid("1");
        permissionOne.setType(map.get("one").getHidden()?1:0);
        permissionTwo.setType(map.get("two").getHidden()?1:0);
        boolean save = aclPermissionService.save(permissionOne);
        if (!save) {
            throw new MyException(20001, "保存一级路由失败");
        }
        permissionTwo.setPid(permissionOne.getId());
        boolean save1 = aclPermissionService.save(permissionTwo);
        if (!save1) {
            throw new MyException(20001, "保存二级路由失败");
        }
        return CommonResult.ok();
    }

    @PostMapping("/menu/addTwo")
    public CommonResult addMenuTwo(@RequestBody MenuVo menuVo) {
        AclPermission permission = new AclPermission();
        BeanUtils.copyProperties(menuVo, permission);
        permission.setType(menuVo.getHidden()?1:0);
        boolean save = aclPermissionService.save(permission);
        if (!save) {
            throw new MyException(20001, "保存二级路由失败");
        }
        return CommonResult.ok();
    }

    @PutMapping("/menu/edit")
    public CommonResult editMenu(@RequestBody MenuVo menuVo) {
        AclPermission permission = new AclPermission();
        BeanUtils.copyProperties(menuVo, permission);
        permission.setType(menuVo.getHidden()?1:0);
        boolean update = aclPermissionService.updateById(permission);
        if (!update) {
            throw new MyException(20001, "更新路由失败");
        }
        return CommonResult.ok();
    }

}
