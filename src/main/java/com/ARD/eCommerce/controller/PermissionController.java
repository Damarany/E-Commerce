package com.ARD.eCommerce.controller;


import com.ARD.eCommerce.dtos.PermissionDto;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.response.ResponseAPI;
import com.ARD.eCommerce.services.permissions.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${request.api.point}/permission")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping("/all")
    public ResponseEntity<ResponseAPI>getAllPermissions(){
        List<PermissionDto> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(new ResponseAPI("done",permissions));
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseAPI>addNewPermission(@RequestBody PermissionDto permissionDto){
        PermissionDto permission = permissionService.insertNewPermission(permissionDto);
        return ResponseEntity.ok(new ResponseAPI("done",permission));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseAPI>deletePermissionById(@PathVariable Long id){
        try {
            permissionService.deleteById(id);
            return ResponseEntity.ok(new ResponseAPI("done",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI("not found",null));
        }

    }
    @DeleteMapping("/delete-all")
    public ResponseEntity<ResponseAPI>deleteAllPermission(){
        try {
            permissionService.deleteAll();
            return ResponseEntity.ok(new ResponseAPI("done",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI("not found",null));
        }

    }


}






