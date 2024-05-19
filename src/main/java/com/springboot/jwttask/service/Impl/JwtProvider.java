package com.springboot.jwttask.service.Impl;

import com.springboot.jwttask.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtProvider {
    public JwtProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private final UserDetailsService userDetailsService;

    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey";

    private final long tokenValidMillisecond = 1000L*60*60;    //1시간 토큰 유효

    public String createToken(String s, List<String> roles) {
        // 여기에 토큰 생성 코드 추가
        return "generatedToken"; // 실제 토큰을 생성하여 반환하도록 수정 필요
    }
}
