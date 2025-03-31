package com.ARD.eCommerce.mapper;

import com.ARD.eCommerce.dtos.ImageDto;
import com.ARD.eCommerce.model.Image;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto imageToImageDto(Image image);
    List<ImageDto> imagesToImageDtos(List<Image> images); // For mapping lists
}
