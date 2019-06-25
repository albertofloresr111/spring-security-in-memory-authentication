package com.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
        PasswordEncoder encoder = passwordEncoder();

        //Return the password encrypted by ByCript
        UserBuilder users = User.builder().passwordEncoder( password -> encoder.encode(password) );

        builder.inMemoryAuthentication()
                .withUser(users.username("admin").password("admin").roles("ADMIN").authorities("ACCESS_TEST1","ACCESS_TEST2","ACCESS_ADMIN"))
                .withUser(users.username("andres").password("andres").roles("USER"))
                .withUser(users.username("manager").password("manager").roles("MANAGER").authorities("ACCESS_TEST1"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .authorizeRequests()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/").authenticated()
                .antMatchers("/greetings").hasRole("USER")
                .antMatchers("/api/v1/test1").hasAuthority("ACCESS_TEST1")
                .antMatchers("/api/v1/test2").hasAuthority("ACCESS_TEST2")
                .antMatchers("/api/v1/admin").hasAuthority("ACCESS_ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }
}