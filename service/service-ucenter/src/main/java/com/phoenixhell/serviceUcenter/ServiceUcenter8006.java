package com.phoenixhell.serviceUcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author phoenixhell
 * @since 2021/3/23 0023-上午 8:48
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
@ComponentScan(basePackages = "com.phoenixhell")
@MapperScan("com.phoenixhell.serviceUcenter.mapper")
public class ServiceUcenter8006 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUcenter8006.class,args);
    }
}
