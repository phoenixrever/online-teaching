package com.phoenixhell.serviceAcl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author phoenixhell
 * @since 2021/3/31 0031-下午 4:27
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "com.phoenixhell")
@MapperScan(basePackages = "com.phoenixhell.serviceAcl.mapper")
public class ServiceAcl8009 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAcl8009.class,args);
    }
}
