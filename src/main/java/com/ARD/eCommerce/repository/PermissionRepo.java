package com.ARD.eCommerce.repository;

import com.ARD.eCommerce.model.Permission;
import com.ARD.eCommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepo extends JpaRepository<Permission,Long> {

    @Query("select p from Permission p " +
            "join RolePermissions rp on rp.permissionId = p.id" +
            " join UserRole ur on ur.roleId = rp.roleId " +
            "where ur.userId = :userId")
    List<Permission> getPermissionByUserId(Long userId);
}
