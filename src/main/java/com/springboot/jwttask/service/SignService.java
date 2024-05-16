package com.springboot.jwttask.service;

import com.springboot.jwttask.dto.SignDto.SignUpDto;
import com.springboot.jwttask.dto.SignDto.SignInResultDto;
import com.springboot.jwttask.dto.SignDto.SignUpResultDto;

public interface SignService {
    SignUpResultDto SignUp(SignUpDto sIgnUpDto, String roles);
    SignInResultDto SignIn(String name, String password);
}
