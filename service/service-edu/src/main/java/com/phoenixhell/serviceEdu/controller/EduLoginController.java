package com.phoenixhell.serviceEdu.controller;

import com.phoenixhell.utils.CommonResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author phoenixhell
 * @since 2021/2/27 0027-下午 4:28
 */
@RestController
@RequestMapping("/serviceEdu")
public class EduLoginController {
    @PostMapping("/login")
    public CommonResult login() {
        return CommonResult.ok().data("token", "admin");
    }

    @GetMapping("/info")
    public CommonResult info() {
        return CommonResult.ok().data("name", "admin").data("roles", "[admin]").data("avatar", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1306508493,2006189766&fm=26&gp=0.jpg");
    }
}
