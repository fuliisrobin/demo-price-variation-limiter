package com.fuli.tradingsystem;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.fuli.tradingsystem")
@EnableAspectJAutoProxy
public class TestConfiguration {
}