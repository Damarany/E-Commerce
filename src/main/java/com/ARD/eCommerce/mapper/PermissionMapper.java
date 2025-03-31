package com.ARD.eCommerce.mapper;

import com.ARD.eCommerce.dtos.PermissionDto;
import com.ARD.eCommerce.dtos.RoleDto;
import com.ARD.eCommerce.dtos.RoleDtoForAssign;
import com.ARD.eCommerce.model.Permission;
import com.ARD.eCommerce.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionDto toDto(Permission permission);
    Permission toEntity(PermissionDto permissionDto);
    List<PermissionDto> toDtoList(List<Permission> permissions);

}
