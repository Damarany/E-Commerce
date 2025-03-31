package com.ARD.eCommerce.services.User;

import com.ARD.eCommerce.dtos.UserDtoToassignRoles;
import com.ARD.eCommerce.exceptions.AlreadyExistsExeption;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.UserMapper;
import com.ARD.eCommerce.model.Role;
import com.ARD.eCommerce.model.User;
import com.ARD.eCommerce.repository.RoleRepo;
import com.ARD.eCommerce.repository.UserRepo;
import com.ARD.eCommerce.request.CreateUserRequest;
import com.ARD.eCommerce.request.UserUpdateRequest;
import com.ARD.eCommerce.services.roles.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    public final UserMapper userMapper;
    public final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserById(Long userId) {
        return userRepo.findById(userId).
                orElseThrow(()->new ResourceNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepo.existsByEmail(request.getEmail()))
                .map(req->{
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setEmail(request.getEmail());
                    return userRepo.save(user);
                }).orElseThrow(()->new AlreadyExistsExeption("Oops!! " + "User already Exist with mail: " + request.getEmail()));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepo.findById(userId).map(existingUser->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepo.save(existingUser);
        }). orElseThrow(()->new ResourceNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.findById(userId).ifPresentOrElse(userRepo::delete,()->{
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        });
    }

    @Override
    public UserDtoToassignRoles assignRolesToUser(Long userId, List<Long> roleIds) {
        User user = getUserById(userId);
        Set<Role> roles = roleRepo.findAllById(roleIds).stream().collect(Collectors.toSet());

        if (user.getRoles() != null ){
            user.getRoles().clear();
            user.setRoles(roles);
        }else{
            user.setRoles(roles);
        }
        User save = userRepo.save(user);
        return userMapper.toUserDtoForAssignRole(save);

    }


}










