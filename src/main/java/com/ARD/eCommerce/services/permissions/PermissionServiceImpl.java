package com.ARD.eCommerce.services.permissions;

import com.ARD.eCommerce.dtos.PermissionDto;
import com.ARD.eCommerce.mapper.PermissionMapper;
import com.ARD.eCommerce.model.Permission;
import com.ARD.eCommerce.repository.PermissionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService{
    private final PermissionRepo permissionRepo;

    private final PermissionMapper permissionMapper;
    @Override
    public List<PermissionDto> getAllPermissions() {
        List<Permission> permissions = permissionRepo.findAll();
        return permissionMapper.toDtoList(permissions);
    }

    @Override
    public PermissionDto insertNewPermission(PermissionDto permissionDto) {
        Permission toEntity = permissionMapper.toEntity(permissionDto);
        toEntity.setName(permissionDto.getName().toUpperCase());
        Permission permission = permissionRepo.save(toEntity);
        return permissionMapper.toDto(permission);
    }

    @Override
    public void deleteById(Long id) {
        permissionRepo.deleteById(id);
    }

    @Override
    public void deleteAll() {
        permissionRepo.deleteAll();
    }


}
