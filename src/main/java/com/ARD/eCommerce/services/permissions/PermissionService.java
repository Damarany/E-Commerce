package com.ARD.eCommerce.services.permissions;

import com.ARD.eCommerce.dtos.PermissionDto;

import java.util.List;

public interface PermissionService {
    List<PermissionDto> getAllPermissions();
    PermissionDto insertNewPermission(PermissionDto permissionDto);

    void deleteById(Long id);
    void deleteAll();
}
