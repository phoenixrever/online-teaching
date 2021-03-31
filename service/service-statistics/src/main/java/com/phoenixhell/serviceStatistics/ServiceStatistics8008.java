package com.phoenixhell.serviceStatistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author phoenixhell
 * @since 2021/3/31 0031-上午 8:44
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan(basePackages = "com.phoenixhell.serviceStatistics.mapper")
@ComponentScan(basePackages = "com.phoenixhell")
@EnableScheduling
public class ServiceStatistics8008 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceStatistics8008.class,args);
    }
}
