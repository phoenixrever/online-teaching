package com.phoenixhell.serviceVod;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.vod")
public class InitConfigProperties {
    private String accessKeyId;
    private String accessKeySecret;
}