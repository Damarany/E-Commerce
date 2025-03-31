package com.ARD.eCommerce.services.roles;

import com.ARD.eCommerce.dtos.RoleDto;
import com.ARD.eCommerce.dtos.RoleDtoForAssign;
import com.ARD.eCommerce.model.Role;

import java.util.List;

public interface RoleService {
    List<RoleDto> getAllRoles();
    RoleDto insertNewRole(RoleDto roleDto);
    RoleDtoForAssign assignPermissonsToRole(Long roleId,List<Long>permissionsId);
    RoleDto modifyRole(Long id,RoleDto roleDto);
    List<Role> getUserRoles(Long userId);

    RoleDto getRoleById(Long id);

    void deleteRoleById(Long id);

    void deleteAllRoles();
}
