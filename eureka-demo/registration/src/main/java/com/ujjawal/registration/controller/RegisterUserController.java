package com.ujjawal.registration.controller;

import com.ujjawal.registration.model.User;
import com.ujjawal.registration.model.UserRole;
import com.ujjawal.registration.repository.UserJpaRepository;
import com.ujjawal.registration.repository.UserRoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "register")
public class RegisterUserController {
    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    UserRoleJpaRepository userRoleJpaRepository;

   /* public RegisterUserController() {
        int i = 10/0;
    }*/

    @PostMapping
    public String registerUser(@RequestBody User user){
        user.setEnabled(Boolean.TRUE);
        userJpaRepository.save(user);
        UserRole userRole = new UserRole();
        userRole.setUserName(user.getUserName());
        userRole.setUserRole("ROLE_USER");
        userRoleJpaRepository.save(userRole);
        return "S";
    }
    @GetMapping(path = "/{username}")
    public User getUser(@PathVariable("username") String username){
        User user = userJpaRepository.findById(username).get();
        return user;
    }
    @GetMapping(path = "/roles/{username}")
    public UserRole getUserRole(@PathVariable("username") String username){
        UserRole userRole = userRoleJpaRepository.findById(username).get();
        return userRole;
    }
}
