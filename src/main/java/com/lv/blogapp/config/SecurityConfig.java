package com.lv.blogapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;


@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Autowired
    JwtFilter jwtfilter;





    @Autowired
    private UserDetailsService userDetailsService;

    
    @Bean
    public SecurityFilterChain securityfiletFilterChain(HttpSecurity http) throws Exception {

        return http
            .csrf(Customizer -> Customizer.disable())
            .authorizeHttpRequests(request -> request
            .requestMatchers("/delete/**","/admin","/admin/**").hasRole("ADMIN")
            .requestMatchers("/register", "/login", "/home","/")
            .permitAll()
            .anyRequest().authenticated())
            // .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(Session -> Session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtfilter,UsernamePasswordAuthenticationFilter.class)
            .build();
    }


    @Bean                                                                                           
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider Provider = new DaoAuthenticationProvider();
        Provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        Provider.setUserDetailsService(userDetailsService);
        return Provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }



}
