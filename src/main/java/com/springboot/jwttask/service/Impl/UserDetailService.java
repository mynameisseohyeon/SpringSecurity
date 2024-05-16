package com.springboot.jwttask.service.Impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailService {

    UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException;
}
