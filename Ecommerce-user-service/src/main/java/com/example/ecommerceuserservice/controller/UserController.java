package com.example.ecommerceuserservice.controller;

import com.example.ecommerceuserservice.dto.UserDto;
import com.example.ecommerceuserservice.service.UserService;
import com.example.ecommerceuserservice.vo.Greeting;
import com.example.ecommerceuserservice.vo.UserRequest;
import com.example.ecommerceuserservice.vo.UserResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class UserController {

    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status(){
        return "It's Working in User Service";    }

    @GetMapping("/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser( @RequestBody UserRequest request ){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map( request , UserDto.class );
        UserResponse userResponse = mapper.map( userService.createUser( userDto ) , UserResponse.class );

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

}
