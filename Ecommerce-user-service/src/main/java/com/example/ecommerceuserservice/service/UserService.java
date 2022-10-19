package com.example.ecommerceuserservice.service;

import com.example.ecommerceuserservice.domain.User;
import com.example.ecommerceuserservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    // 회원가입
    UserDto createUser( UserDto userDto );
    // 유저아이디로 회원찾기
    UserDto getUserByUserId( String userId );
    // 모든 유저 찾기
    Iterable<User> getUserByAll();
}
