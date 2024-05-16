package com.springboot.jwttask.jwt;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;

    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${springboot.jwt.secret}")
    private  String secretKey = "@!#sfsd4tt24856!hj~,#@$ew2351\n";

    private final long tokenValidMillisecond = 1000L*60*60; // 1시간 토큰 유효

    @PostConstruct
    protected void init() {
        logger.info("[init] JwtTokenProvider 내 secretKey 초기화 시작");
        System.out.println(secretKey);

        //서명을 생성하기 위한 키
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        System.out.println(secretKey);
        logger.info("[init] JwtTokenProvider 내 secretKey 초기화 완료");
    }

    //jwt 토큰 생성
    public String createToken(String name, List<String> roles) {
        //uid를 이용하여 jwt 생성
        Claims claims = Jwts.claims().setSubject(name);
        //클레임에 "roles"라는 이름으로 역할 정보(roles 매개변수)를 추가
        claims.put("roles",roles);

        Date now = new Date();
        String token;
        token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
        logger.info("[createToken] 토큰 생성 완료");
        return  token;
    }

    //jwt 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token){
        logger.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                this.getUserName(token));
        logger.info("[getAuthenticaiton] 토큰 인증 정보 조회 완료, UserDetails userName",userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    //회원 구별 정보 추출
    public String getUserName(String token){
        logger.info("[getUsername] 토큰에서 회원 구별 정보 추출");
        String info = Jwts.parser()
                .setSigningKey(secretKey) // 시크릿키로 jwt 검증
                .parseClaimsJws(token) // 토큰 파싱하고 내용 추출
                .getBody() // 토큰 본문 가져오기(payload)
                //Subject -> 토큰 제목 - 토큰에서 사용자에 대한 식별값
                .getSubject(); //클레임에서 "sub" (subject) 필드를 가져와서 회원의 구별 정보를 추출
        logger.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", info);
        return info;
    }

    public String resolveToken(HttpServletRequest request){
        logger.info("[resolveToken] HTTP 헤더에서 Token 값 추출");
        return request.getHeader("X-AUTH-TOKEN");
    }


    //토큰 유효 체크
    public boolean validationToken(String token) {
        logger.info("[validateToken] 토큰 유효 체크 시작");
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            logger.info("[validateToken] 토큰 유효 체크 완료");
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            logger.info("[validateToken] 토큰 유효 체크 예외 발생");
            return false;
        }

    }

}
