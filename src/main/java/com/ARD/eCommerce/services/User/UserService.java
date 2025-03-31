package com.ARD.eCommerce.services.User;

import com.ARD.eCommerce.dtos.UserDtoToassignRoles;
import com.ARD.eCommerce.model.User;
import com.ARD.eCommerce.request.CreateUserRequest;
import com.ARD.eCommerce.request.UserUpdateRequest;

import java.util.List;
import java.util.Set;

public interface UserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
    UserDtoToassignRoles assignRolesToUser(Long userId, List<Long> roleIds);


}
