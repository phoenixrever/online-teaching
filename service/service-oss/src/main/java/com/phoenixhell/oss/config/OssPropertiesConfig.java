package com.phoenixhell.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author phoenixhell
 * @since 2021/3/5 0005-上午 10:54
 */

@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")
@Data
public class OssPropertiesConfig {
    private String endPoint;
    private String keyId;
    private String keySecret;
    private String bucketName;
}
