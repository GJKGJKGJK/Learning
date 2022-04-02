package com.gjk.demo_lean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.gjk.demo_lean.spring.aop.advice.tongyong.TestService;

@SpringBootApplication
public class DemoLeanApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoLeanApplication.class, args);
        TestService testService = context.getBean(TestService.class);
        testService.eatCarrot();
    }

}
