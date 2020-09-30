package com.ujjawal.springsecurity.config;

import com.netflix.discovery.EurekaClient;
import com.ujjawal.springsecurity.config.model.User;
import com.ujjawal.springsecurity.config.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    @Lazy
    EurekaClient eurekaClient;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        int port = eurekaClient.getApplication("registration").getInstances().get(0).getPort();
        ResponseEntity<User> responseEntityUser = new RestTemplate().getForEntity("http://localhost:"+port
                +"/register/"+s,User.class);
        ResponseEntity<UserRole> responseEntityUserRole = new RestTemplate().getForEntity("http://localhost:"+port
                +"/register/roles/"+s,UserRole.class);
        return new org.springframework.security.core.userdetails.User(s, responseEntityUser.getBody().getPassWord(), Arrays.asList(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return responseEntityUserRole.getBody().getUserRole();
            }
        }));

/*        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                ArrayList list = new ArrayList();
                list.add(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ROLE_USER";
                    }
                });
                return list;
            }

            @Override
            public String getPassword() {
                return "$2y$12$6OPbn3RV1ZLk80fM/.lB4.poC2O9Oxq0YcTsGkCHr9I7gjw1AGAn2";
            }

            @Override
            public String getUsername() {
                return "user3";
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };*/
    }
}
