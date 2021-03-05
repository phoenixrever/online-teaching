package com.phoenixhell.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author phoenixhell
 * @since 2021/3/5 0005-上午 11:15
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.phoenixhell")
@EnableSwagger2
public class ServiceOss8002 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOss8002.class,args);
    }
}
