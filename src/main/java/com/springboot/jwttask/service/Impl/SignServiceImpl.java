package com.springboot.jwttask.service.Impl;

import com.springboot.jwttask.dto.CommonResponse;
import com.springboot.jwttask.dto.SignDto.SignInResultDto;
import com.springboot.jwttask.dto.SignDto.SignUpDto;
import com.springboot.jwttask.dto.SignDto.SignUpResultDto;
import com.springboot.jwttask.entity.User;
import com.springboot.jwttask.repository.UserRepository;
import com.springboot.jwttask.service.Impl.JwtProvider;
import com.springboot.jwttask.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class SignServiceImpl implements SignService {
    private Logger logger = LoggerFactory.getLogger(SignServiceImpl.class);

    private JwtProvider jwtProvider;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public SignServiceImpl(UserRepository userRepository , JwtProvider jwtProvider,
                           PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public SignUpResultDto SignUp(SignUpDto signUpDto, String roles){

        User user;
        if(roles.equalsIgnoreCase("admin")){
            user = User.builder()
//                    .email(signUpDto.getEmail())
//                    .number(signUpDto.getNumber())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .name(signUpDto.getName())
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        }else{
            user = User.builder()
//                    .email(signUpDto.getEmail())
//                    .number(signUpDto.getNumber())
                    .password(passwordEncoder.encode(signUpDto.getPassword()))
                    .name(signUpDto.getName())
                    .roles(Collections.singletonList("ROLE_babyLion"))
                    .build();
        }

        User savedbabyLion = userRepository.save(user);

        SignUpResultDto signUpResultDto = new SignUpResultDto();
        logger.info("[getSignResultDto] user 정보 들어왔는지 확인 후 결과값 주입");

//        if(!savedbabyLion.getEmail().isEmpty()){
//            setSucces(signUpResultDto);
//        }else{
//            setFail(signUpResultDto);
//        }

        return signUpResultDto;

    }

    @Override
    public SignInResultDto SignIn(String email, String password)throws RuntimeException{
        User user = userRepository.getByUserId(String.valueOf(id));
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException();
        }
        logger.info("[getSignInResult] 패스워드 일치");

        logger.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
                .token(jwtProvider.createToken(String.valueOf(user.getUserId()),
                        user.getRoles()))
                .build();
        logger.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSucces(signInResultDto);
        return signInResultDto;
    }

    private void setSucces(SignUpResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFail(SignUpResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.Fail.getCode());
        result.setMsg(CommonResponse.Fail.getMsg());

    }

}