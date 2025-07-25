package com.lv.blogapp.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lv.blogapp.Service.CustomUserDetailsService;
import com.lv.blogapp.Service.JwtService;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {


    @Autowired
    private JwtService jwtservices;

    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{

        String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
             jwtToken = authHeader.substring(7);
             username =jwtservices.extractUsername(jwtToken);
            // Here you would typically validate the JWT token and set the authentication in the security context
            // For example:
            // Authentication authentication = jwtService.validateToken(jwtToken);
            // SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
            // Assuming you have a method to validate the JWT token and set the authentication
            // Authentication authentication = jwtService.validateToken(jwtToken);
            // SecurityContextHolder.getContext().setAuthentication(authentication);
           UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(username);
           if(jwtservices.validateToken(jwtToken ,userDetails)){

            UsernamePasswordAuthenticationToken authtoken = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
            authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authtoken);
           }

        }
        filterChain.doFilter(request, response);
       

    }
         




}

