package com.springboot.jwttask.service.Impl;

import com.springboot.jwttask.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        logger.info("[loadUserByUsername] : {} ", userId);
        // 여기에서는 UserDetails 인터페이스를 반환해야 합니다.
        // userRepository.getByUserId(userId)가 User 객체를 반환하므로 이를 UserDetails로 캐스팅해서 반환해야 합니다.
        return (UserDetails) userRepository.getByUserId(userId);
    }
}