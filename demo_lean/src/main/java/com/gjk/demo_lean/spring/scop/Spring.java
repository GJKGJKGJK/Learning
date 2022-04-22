package com.gjk.demo_lean.spring.scop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Spring
 *
 * @ComponentScan 包扫描配置bean
 *
 * @ImportResource xml文件配置bean
 *
 * @author: GJK
 * @date: 2022/4/22 14:29
 * @description:
 */

@Configuration
@ComponentScan("com.gjk.demo_lean.spring.scop")
@ImportResource("classpath:application.xml")
public class Spring {
}
