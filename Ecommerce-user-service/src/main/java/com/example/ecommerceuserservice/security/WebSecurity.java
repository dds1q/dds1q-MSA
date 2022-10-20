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
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private Environment env;

    public WebSecurity(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder,
        Environment env) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
    }

    @Override       // 권한에 관련된 config
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();  //csrf 사용x
        http.authorizeRequests()
            .antMatchers("/users/**").permitAll();
        http.authorizeRequests()
            .antMatchers("/actuator/**").permitAll();  // 회원가입은 비인증 허용
        http.authorizeRequests().antMatchers("/login").permitAll()
                .and()
                .addFilter( getAuthenticationFilter() );

        http.headers().frameOptions().disable();
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter( authenticationManager(), userService, env );
//        authenticationFilter.setAuthenticationManager( authenticationManager());
        return authenticationFilter;
    }

    // select pwd from users where email = ?
    // db_pwd( encrypted ) == input_pwd( encrypted )?
    @Override       // 인증에 관련된 config
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService( userService ).passwordEncoder( bCryptPasswordEncoder );
    }
}
