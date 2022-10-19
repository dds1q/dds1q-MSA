package com.example.ecommerceuserservice.security;

import com.example.ecommerceuserservice.service.UserService;
import javax.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Environment env;

    @Override       // 권한에 관련된 config
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();  //csrf 사용x
//        http.authorizeRequests()
//            .antMatchers("/users/**").permitAll();  // 로그인, 회원가입은 비인증 허용
        http.authorizeRequests().antMatchers("/**")
                .hasIpAddress("192.168.45.169") // 통과시키고 싶은 IP
                .and()
                .addFilter( getAuthenticationFilter() );

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager( authenticationManager());

        return authenticationFilter;
    }

    // select pwd from users where email = ?
    // db_pwd( encrypted ) == input_pwd( encrypted )?
    @Override       // 인증에 관련된 config
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( userService ).passwordEncoder( bCryptPasswordEncoder );
    }
}
