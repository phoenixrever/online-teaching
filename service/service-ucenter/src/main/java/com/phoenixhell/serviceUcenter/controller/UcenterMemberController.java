package com.phoenixhell.serviceUcenter.controller;


import com.phoenixhell.serviceUcenter.entity.UcenterMember;
import com.phoenixhell.serviceUcenter.entity.vo.LoginVO;
import com.phoenixhell.serviceUcenter.entity.vo.RegisterVo;
import com.phoenixhell.serviceUcenter.service.UcenterMemberService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author phoenixhell
 * @since 2021-03-23
 */
@RestController
@RequestMapping("/serviceUcenter/member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("/login")
    public CommonResult login(@RequestBody LoginVO loginVO){
        String token=ucenterMemberService.login(loginVO);
        return CommonResult.ok().data("token",token);
    }

    @PostMapping("/register")
    public CommonResult register(@RequestBody RegisterVo registerVo){
        String token=ucenterMemberService.register(registerVo);
        return CommonResult.ok().data("token",token);
    }
    @GetMapping("/info")
    public CommonResult getUserInfoByToken(HttpServletRequest request){
        UcenterMember user =ucenterMemberService.getUserInfoByToken(request);
        user.setPassword(null);
        return CommonResult.ok().data("user",user);
    }

    @GetMapping("/feignInfo/{userId}")
    public CommonResult getUserInfoByTokenId( @PathVariable String userId){
        UcenterMember userInfo =ucenterMemberService.getUserInfoByTokenId(userId);
        userInfo.setPassword(null);
        return CommonResult.ok().data("userInfo",userInfo);
    }

    @GetMapping("/registerCount/{day}")
    public Integer registerCount(@PathVariable String day){
        Integer count = ucenterMemberService.query().eq("DATE(gmt_create)", day).count();
        return count;
    }
}

