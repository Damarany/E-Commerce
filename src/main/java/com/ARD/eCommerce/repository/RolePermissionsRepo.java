package com.ARD.eCommerce.repository;

import com.ARD.eCommerce.model.Permission;
import com.ARD.eCommerce.model.RolePermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionsRepo extends JpaRepository<RolePermissions,Long> {

}
