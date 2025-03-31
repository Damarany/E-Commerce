package com.ARD.eCommerce.repository;

import com.ARD.eCommerce.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageRepo extends JpaRepository<Image,Long> {
//  select i.download_url as url, i.image as img,p.name from product p join image i on i.product_id = p.id where p.id = 2
    @Query("select i,p.name from Product p " +
            "join Image i on p.id = i.product.id " +
            "where p.id = :productId")
    Optional<List<Image>> findimageByProductId(Long productId);
}
