package com.example.ecommerceuserservice.controller;

import com.example.ecommerceuserservice.domain.User;
import com.example.ecommerceuserservice.dto.UserDto;
import com.example.ecommerceuserservice.service.UserService;
import com.example.ecommerceuserservice.vo.Greeting;
import com.example.ecommerceuserservice.dto.UserRequest;
import com.example.ecommerceuserservice.dto.UserResponse;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/health_check")    // 상태체크, 포트확인
    public String status(){
        return String.format( "It's Working in User Service on PORT %s", env.getProperty("local.server.port") );
    }

    @GetMapping("/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")  // 회원가입
    public ResponseEntity<UserResponse> createUser( @RequestBody UserRequest request ){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map( request , UserDto.class );
        UserResponse userResponse = mapper.map( userService.createUser( userDto ) , UserResponse.class );

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers(){

        Iterable<User> users = userService.getUserByAll();
        List<UserResponse> result = new ArrayList<>();

        users.forEach( v -> {
            result.add( new ModelMapper().map( v , UserResponse.class ) );
        });

        return ResponseEntity.status( HttpStatus.OK ).body( result );
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUser( @PathVariable String userId ){

        UserDto userDto = userService.getUserByUserId( userId );

        UserResponse userResponse = new ModelMapper().map( userDto , UserResponse.class );

        return ResponseEntity.status( HttpStatus.OK ).body( userResponse );
    }


}
