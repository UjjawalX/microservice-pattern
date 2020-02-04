package com.ujjawal.spring.cloud.eureka.client;

import org.springframework.web.bind.annotation.RequestMapping;

interface GreetingController {
    @RequestMapping("/getGreeting")
    String getGreeting();
}