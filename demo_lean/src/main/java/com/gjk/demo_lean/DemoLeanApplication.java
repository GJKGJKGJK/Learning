package com.gjk.demo_lean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.gjk.demo_lean.spring.aop.advice.tongyong.IService;
import com.gjk.demo_lean.spring.aop.advice.tongyong.TestService;
import com.gjk.demo_lean.spring.aop.advice.tongyong.TestService1;

@SpringBootApplication
public class DemoLeanApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoLeanApplication.class, args);
//        TestService testService = context.getBean(TestService.class);
//        testService.eatCarrot();
        TestService1 testService1 = context.getBean(TestService1.class);
        IService service = (IService) testService1;
        service.eatCarrot();
        testService1.brushTeeth();
    }

}
