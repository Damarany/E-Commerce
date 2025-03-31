package com.ARD.eCommerce.repository;

import com.ARD.eCommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);
    User findByEmail(String email);
}
