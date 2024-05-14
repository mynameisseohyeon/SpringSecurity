package com.springboot.jwttask.service.Impl;

import com.springboot.jwttask.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailService{

    private Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);



    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        logger.info("[loadUserByUsername] : {} ",userId);
        return (UserDetails) userRepository.getByUserId(userId); //(UserDetails) 추가
    }
}
