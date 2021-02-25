package com.phoenixhell.serviceEdu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author phoenixhell
 * @create 2021/2/25 0025-下午 1:02
 */
@SpringBootApplication
@MapperScan(basePackages = "com.phoenixhell.serviceEdu.mapper")
@ComponentScan(basePackages = "com.phoenixhell")
@EnableSwagger2
public class ServiceEdu8001 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceEdu8001.class,args);
    }
}
