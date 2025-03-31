package com.ARD.eCommerce.controller;

import com.ARD.eCommerce.dtos.RoleDto;
import com.ARD.eCommerce.dtos.RoleDtoForAssign;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.response.ResponseAPI;
import com.ARD.eCommerce.services.roles.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${request.api.point}/roles")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('VIEW-PRODUCT')")
    public ResponseEntity<ResponseAPI> getAllRoles(){
        List<RoleDto> rolesDto = roleService.getAllRoles();
        return ResponseEntity.ok(new ResponseAPI("done",rolesDto));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseAPI> addNewRole(@RequestBody RoleDto roleDto){
        RoleDto role = roleService.insertNewRole(roleDto);
        return ResponseEntity.ok(new ResponseAPI("done",role));
    }


    @PatchMapping("/{roleId}/assign")
    public ResponseEntity<ResponseAPI>assignPermissionsToRole(@PathVariable Long roleId,@RequestParam List<Long>permissionsId){
        try {
            RoleDtoForAssign role = roleService.assignPermissonsToRole(roleId, permissionsId);
            return ResponseEntity.ok(new ResponseAPI("done",role));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(e.getMessage(), null));
        }
    }

    @PutMapping("/role/{roleId}")
    public ResponseEntity<ResponseAPI>updateRole(@Valid @RequestBody RoleDto roleDto, @PathVariable("roleId")Long roleId){
        roleService.modifyRole(roleId,roleDto);
        return ResponseEntity.ok(new ResponseAPI("done",roleDto));    }

    @GetMapping("/userroles/{userId}")
    public ResponseEntity<?>getUserRoles(@PathVariable("userId")Long userId){
        return ResponseEntity.ok(roleService.getUserRoles(userId));
    }


    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseAPI> deleteRoleById(@PathVariable Long id){
        roleService.deleteRoleById(id);
        return  ResponseEntity.ok(new ResponseAPI("done",null));
    }

    @DeleteMapping("/delete-all")
    ResponseEntity<ResponseAPI> deleteAllRoles(){
        roleService.deleteAllRoles();
        return  ResponseEntity.ok(new ResponseAPI("done",null));
    }


}














