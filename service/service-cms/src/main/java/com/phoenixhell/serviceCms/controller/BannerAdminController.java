package com.phoenixhell.serviceCms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenixhell.serviceCms.entity.CrmBanner;
import com.phoenixhell.serviceCms.service.CrmBannerService;
import com.phoenixhell.servicebase.exceptionhandler.MyException;
import com.phoenixhell.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author phoenixhell
 * @since 2021-03-20
 */
@RestController
@RequestMapping("/serviceCms/bannerAdmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("/get/{id}")
    public CommonResult get(@PathVariable("id")String id){
        CrmBanner banner = crmBannerService.getById(id);
        return CommonResult.ok().emptyData().data("banner",banner);
    }

    @GetMapping("/list")
    //包不要导错
//    @Cacheable(value = "banner",key = "'list'")
    //清空value指定名字的缓存
    @CacheEvict(value = "homePage", allEntries=true)
    public CommonResult list(){
        List<CrmBanner> list = crmBannerService.list();
        return CommonResult.ok().emptyData().data("list",list);
    }
    @GetMapping("/page/{currentPage}")
    public CommonResult page(@PathVariable("currentPage")Long currentPage,@RequestParam("limit") Long limit){
        Page<CrmBanner> page = crmBannerService.page(new Page<CrmBanner>(currentPage, limit));
        return CommonResult.ok().emptyData().data("list",page);
    }
    @PostMapping("/add")
    public CommonResult add(@RequestBody CrmBanner crmBanner){
        boolean save = crmBannerService.save(crmBanner);
        if(!save){
            throw new MyException(20001,"插入失败");
        }
        return CommonResult.ok().emptyData();
    }
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id")String id){
        boolean remove = crmBannerService.removeById(id);
        if(!remove){
            throw new MyException(20001,"删除失败");
        }
        return CommonResult.ok().emptyData();
    }
    @PutMapping("/edit/{id}")
    public CommonResult edit(@RequestBody CrmBanner banner){
        boolean update = crmBannerService.updateById(banner);
        if(!update){
            throw new MyException(20001,"编辑失败");
        }
        return CommonResult.ok().emptyData();
    }

}

