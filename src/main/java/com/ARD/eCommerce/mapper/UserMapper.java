package com.ARD.eCommerce.mapper;

import com.ARD.eCommerce.dtos.UserDto;
import com.ARD.eCommerce.dtos.UserDtoToassignRoles;
import com.ARD.eCommerce.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,uses = OrderMapper.class)
public interface UserMapper {
    @Mapping(source = "cart",target = "cart")
    @Mapping(source = "orders",target = "orders")
    public UserDto toUserDto(User user);

    public UserDtoToassignRoles toUserDtoForAssignRole( User user);
}
