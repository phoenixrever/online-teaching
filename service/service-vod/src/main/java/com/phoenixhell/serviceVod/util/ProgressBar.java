package com.phoenixhell.serviceVod.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author phoenixhell
 * @since 2021/3/17 0017-上午 10:41
 */
@Data
@Component
public class ProgressBar {
    private Map<String,Integer> percentMap;
    public ProgressBar(){
        percentMap=new HashMap<>();
        percentMap.put("admin",0);
    }
}
