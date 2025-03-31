package com.ARD.eCommerce.dtos;

import com.ARD.eCommerce.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
//    private String categoryName;
    private Category category;
    private List<ImageDto> images;
}
