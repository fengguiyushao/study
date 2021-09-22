package com.robinzhou.websec;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "com.robinzhou")
@MapperScan("com.robinzhou.websec.mapper")
public class WebsecApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebsecApplication.class, args);
    }

}
