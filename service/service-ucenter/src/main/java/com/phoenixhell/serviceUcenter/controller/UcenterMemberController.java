package com.phoenixhell.serviceUcenter.controller;


import com.phoenixhell.serviceUcenter.entity.UcenterMember;
import com.phoenixhell.serviceUcenter.entity.vo.LoginVO;
import com.phoenixhell.serviceUcenter.entity.vo.RegisterVo;
import com.phoenixhell.serviceUcenter.service.UcenterMemberService;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    @PostMapping("/login")
    public CommonResult login(@RequestBody LoginVO loginVO){
        String token=ucenterMemberService.login(loginVO);
        return CommonResult.ok().emptyData().data("token",token);
    }

    @PostMapping("/register")
    public CommonResult register(@RequestBody RegisterVo registerVo){
        String token=ucenterMemberService.register(registerVo);
        return CommonResult.ok().emptyData().data("token",token);
    }
}

