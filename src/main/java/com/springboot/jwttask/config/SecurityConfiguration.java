package com.springboot.jwttask.config;

import com.springboot.jwttask.handler.CustomAccessDeniedHandler;
import com.springboot.jwttask.handler.CustomAuthenticationEntryPoint;
import com.springboot.jwttask.jwt.JwtAuthenticationFilter;
import com.springboot.jwttask.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig (JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.httpBasic().disable() // RestAPI는 UI를 사용하지 않기 때문에, 기본설정을 비활성화
                .csrf().disable() //RestAPI는 csrf 보안이 필요 없으므로 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //JwtToken 인증방식이므로 세션을 사용하지 않으므로 비활성화
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/sign-api/sign-in","/sign-api/sign-up","/sign-api/exception")
                .permitAll() //가입과 로그인 주소는 허용
                .antMatchers(HttpMethod.GET,"/Board/**").permitAll()
                //product로 시작하는 GET 요청은 허용
                .antMatchers("**exception**").permitAll()
                .anyRequest().hasRole("ADMIN") //나머지 요청은 ADMIN만 접근 가능
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception");
    }

}
