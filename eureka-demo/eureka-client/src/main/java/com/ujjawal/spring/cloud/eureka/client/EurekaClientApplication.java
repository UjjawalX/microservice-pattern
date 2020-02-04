package com.ujjawal.spring.cloud.eureka.client;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableEurekaClient
@Controller
class EurekaClientApplication implements GreetingController {
    @Autowired
    @Lazy
    private EurekaClient eurekaClient;
    @Value("${spring.application.name}")
    private String appName;
    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
    @Override
    @RequestMapping("/getGreeting")
    public String getGreeting(){
        return String.format("Hello from %s",eurekaClient.getApplication(appName).getName());
    }
}