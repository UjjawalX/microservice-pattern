package com.ujjawal.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;
    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/login/**").permitAll()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/**").hasRole("USER")
                .and().formLogin().loginPage("/login").and().csrf().disable();//.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        //Code for reference
        /*http
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll().anyRequest().authenticated().and()
                .formLogin()
                    .loginPage("/login").permitAll().and()
                .logout()
                    .permitAll();*/
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication().withUser("user").password(passwordEncoder().encode("a")).roles("USER").and()
                .withUser("admin").password((passwordEncoder().encode("a"))).roles("ADMIN", "USER");*/
        //auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select user_name,pass_word,enabled from demodb.user where user_name = ?").authoritiesByUsernameQuery("select user_name,role from demodb.user_role where user_name = ? ");
        auth.userDetailsService(customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build();
        return new InMemoryUserDetailsManager(user);
    }*/
}
