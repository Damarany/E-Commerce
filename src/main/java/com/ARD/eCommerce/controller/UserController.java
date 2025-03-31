package com.ARD.eCommerce.controller;

import com.ARD.eCommerce.dtos.UserDto;
import com.ARD.eCommerce.dtos.UserDtoToassignRoles;
import com.ARD.eCommerce.exceptions.AlreadyExistsExeption;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.UserMapper;
import com.ARD.eCommerce.model.User;
import com.ARD.eCommerce.request.CreateUserRequest;
import com.ARD.eCommerce.request.UserUpdateRequest;
import com.ARD.eCommerce.response.ResponseAPI;
import com.ARD.eCommerce.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("${request.api.point}/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ResponseAPI> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userMapper.toUserDto(user);
            return ResponseEntity.ok(new ResponseAPI("done",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseAPI("Error MSG",e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseAPI>createUser(@RequestBody CreateUserRequest request){
        try {
            User user = userService.createUser(request);
            UserDto userDto = userMapper.toUserDto(user);
            return ResponseEntity.ok(new ResponseAPI("done",userDto));
        } catch (AlreadyExistsExeption e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseAPI("Error MSG",e.getMessage()));
        }
    }

    @PutMapping({"/userId/update"})
    public ResponseEntity<ResponseAPI>updateUser(@RequestBody UserUpdateRequest request,@PathVariable Long userId){
        try {
        User user = userService.updateUser(request, userId);
            UserDto userDto = userMapper.toUserDto(user);
        return ResponseEntity.ok(new ResponseAPI("done",userDto));
    } catch (AlreadyExistsExeption e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseAPI("Error MSG",e.getMessage()));
    }
    }

    @DeleteMapping({"/userId/delete"})
    public ResponseEntity<ResponseAPI>deleteUser(@RequestBody UserUpdateRequest request,Long userId){
        try {
             userService.deleteUser(userId);
            return ResponseEntity.ok(new ResponseAPI("Deleted for userID: " + userId,null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseAPI("Error MSG",e.getMessage()));
        }
    }

    @PatchMapping("/user/assign-roles/{userId}")
    public ResponseEntity<ResponseAPI>assignRolesForUser(@RequestParam List<Long> roleIds, @PathVariable Long userId ){
        UserDtoToassignRoles userDtoToassignRoles = userService.assignRolesToUser(userId, roleIds);
        return ResponseEntity.ok(new ResponseAPI("Done",userDtoToassignRoles));

    }
}
















