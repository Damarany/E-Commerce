package com.ARD.eCommerce.mapper;

import com.ARD.eCommerce.dtos.CategoryDto;
import com.ARD.eCommerce.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    Category DtoToCategory(CategoryDto categoryDto);

    Category updateCategoryDtoToCategory(CategoryDto categoryDto,@MappingTarget Category category);
}
