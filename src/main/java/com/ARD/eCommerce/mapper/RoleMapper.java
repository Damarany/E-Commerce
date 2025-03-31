package com.ARD.eCommerce.mapper;

import com.ARD.eCommerce.dtos.RoleDto;
import com.ARD.eCommerce.dtos.RoleDtoForAssign;
import com.ARD.eCommerce.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
    List<RoleDto> toDtoList(List<Role> roles);

    Role fromUpdateDtoToRole(RoleDto roleDto,@MappingTarget Role role);

    RoleDtoForAssign toRoleDtoForAssign(Role role);

}
