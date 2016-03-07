package com.postman;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    private static final Logger LOGGER = LogManager.getLogger(SecurityConfig.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public ShaPasswordEncoder shaPasswordEncoder(){
        return new ShaPasswordEncoder();
    }

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(shaPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/users/add", "/home").permitAll()
                .antMatchers("/**").authenticated();
        http.formLogin()
                .loginPage("/home")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/home?error")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .successHandler(authenticationSuccessHandler)
                .permitAll();
        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/home?logout")
                .invalidateHttpSession(true);

    }
}
