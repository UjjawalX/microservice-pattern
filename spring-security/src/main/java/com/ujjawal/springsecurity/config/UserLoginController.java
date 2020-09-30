package com.ujjawal.springsecurity.config;

import com.netflix.discovery.EurekaClient;
import com.ujjawal.springsecurity.config.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.util.UriTemplateHandler;
import org.thymeleaf.spring5.view.ThymeleafView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@EnableEurekaClient
public class UserLoginController {
    @Autowired
            @Lazy
    EurekaClient eurekaClient;

    @Autowired
    PasswordEncoder encoder;

    @RequestMapping(value = "/signup")
    public String register(@RequestParam(value = "username") String username,@RequestParam(value= "password") String password,@RequestParam(value ="emailId") String emailId) {
        Log LOG = LogFactory.getLog(UserLoginController.class);
        LOG.info("My username"+username);
//        new RequestEntity<>();
//        RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080/register").build();
//        restTemplate.postForObject("http://localhost:8080/register",);
//        RequestEntity<User> userEntity = new RequestEntity<User>(new User());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> userEntity = new HttpEntity<>(new User().setUserName(username).setPassWord(encoder.encode(password)).setEmailId(emailId),httpHeaders);
        int port = eurekaClient.getApplication("registration").getInstances().get(0).getPort();
        ResponseEntity<String> responseEntity = new RestTemplate().postForEntity("http://localhost:"+port
                +"/register",userEntity,String.class);
        return "login";
    }
}





