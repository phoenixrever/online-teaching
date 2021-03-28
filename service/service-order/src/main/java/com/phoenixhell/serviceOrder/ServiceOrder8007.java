package com.phoenixhell.serviceOrder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "com.phoenixhell")
@MapperScan(basePackages = "com.phoenixhell.serviceOrder.mapper")
@EnableDiscoveryClient
@EnableFeignClients
public class ServiceOrder8007 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrder8007.class, args);
    }
}
