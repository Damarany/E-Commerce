package com.ARD.eCommerce.repository;

import com.ARD.eCommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

    @Query("select r from UserRole ur " +
            "join Role r on ur.roleId = r.id where ur.userId = :userId ")
    List<Role> getUserRoles(Long userId);
}
