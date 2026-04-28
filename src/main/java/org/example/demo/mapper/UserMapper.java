package org.example.demo.mapper;

import org.example.demo.dto.response.UserResponse;
import org.example.demo.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
    List<UserResponse> toUserResponse(List<User> users);
}
