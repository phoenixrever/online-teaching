package com.phoenixhell.serviceUcenter.entity.vo;

import lombok.Data;

/**
 * @author phoenixhell
 * @since 2021/3/23 0023-上午 10:01
 */
@Data
public class LoginVO {
    private String emailOrMobile;
    private String password;
}
