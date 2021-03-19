package com.phoenixhell.serviceEdu.vodClient;

import com.phoenixhell.utils.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author phoenixhell
 * @since 2021/3/19 0019-上午 10:46
 */
@Component
@FeignClient(value="server-vod")
public interface VodService {
    @DeleteMapping("/serviceVod/video/delete/{videoId}")
    public CommonResult deleteByVideoId(@PathVariable("videoId") String videoId);
}
