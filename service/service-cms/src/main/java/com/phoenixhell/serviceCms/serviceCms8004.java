package com.phoenixhell.serviceCms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author phoenixhell
 * @since 2021/3/20 0020-下午 2:38
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@MapperScan("com.phoenixhell.serviceCms.mapper")
@ComponentScan(basePackages = "com.phoenixhell")
public class serviceCms8004 {
    public static void main(String[] args) {
        SpringApplication.run(serviceCms8004.class,args);
    }
}
