package com.phoenixhell.serviceUcenter.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author phoenixhell
 * @since 2021/3/25 0025-上午 8:05
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx.open")
public class WxProperties {
    private String appId;
    private String appSecret;
    private String redirectUrl;
}
