package com.example.ecommerceuserservice.security;

import com.example.ecommerceuserservice.domain.User;
import com.example.ecommerceuserservice.dto.LoginRequest;
import com.example.ecommerceuserservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService userService,
                                Environment env) {
        super.setAuthenticationManager(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest creds = new ObjectMapper().readValue( request.getInputStream(), LoginRequest.class );

            // 생성된 인증용 토큰을 authentication manager에게 전송하여 인증요청
            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(    // 인증용 토큰 생성
                    creds.getEmail(),
                    creds.getPassword(),
                    new ArrayList<>()   // arraylist에는 어떤 권한을 가질 것인지 정보를 담음
                )
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override   // 로그인 성공시 handler
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        {

        String userId =  ((UserDetailsImpl)authResult.getPrincipal()).getUser().getUserId();

        String token = Jwts.builder()
            .setSubject( userId )
            .setExpiration( new Date( System.currentTimeMillis()
                + Long.parseLong(env.getProperty("token.expiration_time"))))
            .signWith( SignatureAlgorithm.HS512, env.getProperty("token.secret") )
            .compact( );

        response.addHeader("token", token );
        response.addHeader("userId", userId );

    }
}
