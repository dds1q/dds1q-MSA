package com.example.ecommerceuserservice.service;

import com.example.ecommerceuserservice.domain.User;
import com.example.ecommerceuserservice.dto.OrderResponse;
import com.example.ecommerceuserservice.dto.UserDto;
import com.example.ecommerceuserservice.repository.UserRepository;
import com.example.ecommerceuserservice.security.UserDetailsImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail( username ).orElseThrow(
            () -> new UsernameNotFoundException("USER_NOT_FOUND")
        );

        return new UserDetailsImpl( user );
    }

    @Override    // 회원가입
    public UserDto createUser(UserDto userDto) {

        userDto.setUserId( UUID.randomUUID().toString() );

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);   //mapper 규칙:엄격하게로 설정
        User user = mapper.map( userDto, User.class ); // UserDto 객체를 User 객체로 변환
        user.setEncryptedPwd( passwordEncoder.encode( userDto.getPwd() ) );
        userRepository.save( user );

        return mapper.map( user , UserDto.class );
    }

    @Override
    public UserDto getUserByUserId(String userId) {

        User user = userRepository.findByUserId( userId ).orElseThrow(
            () -> new UsernameNotFoundException("User not found")
        );

        UserDto userDto = new ModelMapper().map( user , UserDto.class );

        List<OrderResponse> orders = new ArrayList<>();
        userDto.setOrders( orders );

        return userDto;
    }

    @Override
    public Iterable<User> getUserByAll() {
        return userRepository.findAll();
    }

}
