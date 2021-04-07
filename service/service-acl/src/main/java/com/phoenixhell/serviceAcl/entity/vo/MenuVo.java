package com.phoenixhell.serviceAcl.entity.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author phoenixhell
 * @since 2021/4/6 0006-下午 4:17
 */
@Data
public class MenuVo implements Comparable<MenuVo> {
    private String id;
    private String path;
    private String component;
    private String redirect;
    private String name;
    private Map<String,String> meta;
    private String icon;
    private Integer sort;
    private Boolean hidden=false;
    private List<MenuVo> children;

    @Override
    public int compareTo(MenuVo o) {
        return this.getSort()-o.getSort();
    }
}
