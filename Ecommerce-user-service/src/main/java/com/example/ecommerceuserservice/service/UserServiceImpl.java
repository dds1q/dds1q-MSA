package com.example.ecommerceuserservice.service;

import com.example.ecommerceuserservice.domain.User;
import com.example.ecommerceuserservice.dto.UserDto;
import com.example.ecommerceuserservice.repository.UserRepository;
import com.example.ecommerceuserservice.vo.UserRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {

        userDto.setUserId( UUID.randomUUID().toString() );

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = mapper.map( userDto, User.class );
        user.setEncryptedPwd( "encrypted_password");
        userRepository.save( user );

        UserDto returnUserDto = mapper.map( user , UserDto.class );

        return returnUserDto;
    }
}
