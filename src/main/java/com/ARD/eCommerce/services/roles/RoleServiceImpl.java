package com.ARD.eCommerce.services.roles;

import com.ARD.eCommerce.dtos.RoleDto;
import com.ARD.eCommerce.dtos.RoleDtoForAssign;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.RoleMapper;
import com.ARD.eCommerce.model.Permission;
import com.ARD.eCommerce.model.Role;
import com.ARD.eCommerce.repository.PermissionRepo;
import com.ARD.eCommerce.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;
    private final RoleMapper roleMapper;

    private final PermissionRepo permissionRepo;
    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> role = roleRepo.findAll();
        return roleMapper.toDtoList(role);
    }

    @Override
    public RoleDto insertNewRole(RoleDto roleDto) {
        Role role = roleMapper.toEntity(roleDto);
        role.setName(role.getName().toUpperCase());
        Role save = roleRepo.save(role);
        return roleMapper.toDto(save);
    }

    @Override
    public RoleDtoForAssign assignPermissonsToRole(Long roleId, List<Long> permissionsId) {
        //get role by id
        Role role  = roleRepo.findById(roleId).orElseThrow(()->new ResourceNotFoundException("Role not found"));

        //get all permission you want to assign
        Set<Permission> permissions = permissionRepo.findAllById(permissionsId).stream().collect(Collectors.toSet());

        if(role.getPermissions() !=null){
            role.getPermissions().clear();
            role.getPermissions().addAll(permissions);
        }else{
            Set<Permission>newPermission = new HashSet<>();
            newPermission.addAll(permissions);
            role.setPermissions(newPermission);
        }
        Role savedRole = roleRepo.save(role);

        return roleMapper.toRoleDtoForAssign(savedRole);
    }

    @Override
    public RoleDto modifyRole(Long id, RoleDto roleDto) {
        Role roleEntity = roleRepo.findById(id).orElseThrow(()->new RuntimeException("wrong Role"));
        roleMapper.fromUpdateDtoToRole(roleDto,roleEntity);
        roleRepo.save(roleEntity);
        return roleDto;
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        return roleRepo.getUserRoles(userId);
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role role = roleRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("role not found"));
        return roleMapper.toDto(role);
    }

    @Override
    public void deleteRoleById(Long id) {
        roleRepo.deleteById(id);
    }

    @Override
    public void deleteAllRoles() {
        roleRepo.deleteAll();
    }


}
