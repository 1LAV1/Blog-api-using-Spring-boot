package com.lv.blogapp.Entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    private Users user;

    public UserPrincipal(Users user) {
        this.user = user;
    }
    public Users getUser() {
        return user;
    }
    



    // Implement the methods from UserDetails interface
    // For example:
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the authorities granted to the user
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())); // Replace with actual implementation
    }

    @Override
    public String getPassword() {
        // Return the user's password
        return user.getPassword(); // Replace with actual implementation
    }

    @Override
    public String getUsername() {
        // Return the user's username
        return user.getUsername(); // Replace with actual implementation
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Replace with actual logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Replace with actual logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Replace with actual logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Replace with actual logic
    }

}
