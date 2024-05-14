package com.springboot.jwttask.entity;


import java.io.Serializable;
import java.util.Collection;

public interface UserDetail extends Serializable {

    //Collection<? extends GrantedAuthority> getAuthorities();

    String getUserId();

    String getPassword();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();

    boolean isEnabled();
}
