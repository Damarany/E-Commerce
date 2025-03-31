package com.ARD.eCommerce.repository;

import com.ARD.eCommerce.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole,Long> {

}
