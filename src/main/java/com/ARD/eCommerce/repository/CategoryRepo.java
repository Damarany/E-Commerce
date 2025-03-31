package com.ARD.eCommerce.repository;

import com.ARD.eCommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Long> {
    Optional<Category> findByName(String name);
    Boolean existsByName(String name);

}
