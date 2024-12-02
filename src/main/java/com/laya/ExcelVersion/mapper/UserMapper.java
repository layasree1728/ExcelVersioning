package com.laya.ExcelVersion.mapper;

import com.laya.ExcelVersion.dto.UserDTO;
import com.laya.ExcelVersion.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);
}
