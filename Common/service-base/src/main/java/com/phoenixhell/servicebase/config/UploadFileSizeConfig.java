package com.phoenixhell.servicebase.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @author phoenixhell
 * @since 2021/3/17 0017-上午 9:38
 */

//@Configuration//被下面配置替代2个都要有
// max-file-size: 1000MB
// max-request-size: 1000MB
public class UploadFileSizeConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大20000M
        factory.setMaxFileSize(DataSize.ofMegabytes(1000));
        //        factory.setMaxFileSize(DataSize.parse("100MB"));
        // 设置总上传数据总大小

        factory.setMaxRequestSize(DataSize.parse("50000MB"));
        return factory.createMultipartConfig();
    }
}
