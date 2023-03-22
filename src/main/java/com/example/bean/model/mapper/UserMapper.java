package com.example.bean.model.mapper;

import com.example.bean.model.dto.UserDto;
import com.example.bean.model.User;
import org.modelmapper.ModelMapper;


public class UserMapper {

    public static UserDto toUserDto (User user) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user,UserDto.class);
    }
}
