package com.phoenixhell.serviceEdu.vodClient;

import com.phoenixhell.utils.CommonResult;
import org.springframework.stereotype.Component;

@Component
public class VodServiceFallBack implements VodService {
    @Override
    public CommonResult deleteByVideoId(String videoId) {
        return CommonResult.error().data("fallback","callback service");
    }
}
