package com.phoenixhell.serviceAcl.entity.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author phoenixhell
 * @since 2021/4/6 0006-下午 4:17
 */
@Data
public class MenuVo {
    private String id;
    private String path;
    private String component;
    private String redirect;
    private String name;
    private Map<String,String> meta;
    private String icon;
    private Boolean hidden=false;
    private List<MenuVo> children;
}
