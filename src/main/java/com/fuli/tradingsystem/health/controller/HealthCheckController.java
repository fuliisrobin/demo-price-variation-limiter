package com.fuli.tradingsystem.health.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController

@RequestMapping("/")
public class HealthCheckController {
    
    @GetMapping("/health-check")
    int healthCheck() {
	return 0;
    }
}
