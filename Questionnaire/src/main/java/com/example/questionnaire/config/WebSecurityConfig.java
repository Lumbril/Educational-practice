package com.example.questionnaire.config;

import com.example.questionnaire.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserServiceImpl userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                    .disable()
                .authorizeRequests()
                    //Доступ только для не зарегистрированных пользователей
                    .antMatchers("/registration").not().fullyAuthenticated()
                    //Доступ только для пользователей с ролью Администратор
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    //Доступ для пользователь с ролью Юзер
                    .antMatchers("/test/user", "/question/**").hasRole("USER")
                    //Доступ разрешен всем пользователей
                    .antMatchers(
                            "/", "/login", "/resources/**", "/static/**", "/test/**", "/question/**",
                            "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"
                    ).permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
