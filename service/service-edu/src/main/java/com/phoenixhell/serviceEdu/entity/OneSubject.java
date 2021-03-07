package com.phoenixhell.serviceEdu.entity;

import lombok.Data;

import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/3/7 0007-上午 8:46
 */
@Data
public class OneSubject {
    private String id;
    private String label;
//    private String parentId;
    private List<TwoSubject> children;
}
