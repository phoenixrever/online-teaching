package com.phoenixhell.serviceVod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = "com.phoenixhell")
@EnableSwagger2
public class ServiceVod8003 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVod8003.class, args);
    }
}
