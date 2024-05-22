package com.example.emtesttask.mapper;

import com.example.emtesttask.dto.UserDTO;
import com.example.emtesttask.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDTO>{
}
