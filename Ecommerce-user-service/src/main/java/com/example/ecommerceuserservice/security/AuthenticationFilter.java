package com.example.ecommerceuserservice.security;

import com.example.ecommerceuserservice.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
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

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult)
        throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
