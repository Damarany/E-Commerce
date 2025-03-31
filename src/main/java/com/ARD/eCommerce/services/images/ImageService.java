package com.ARD.eCommerce.services.images;

import com.ARD.eCommerce.dtos.ImageDto;
import com.ARD.eCommerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);

    List<ImageDto>getImagesByProductId(Long productId);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(MultipartFile file,  Long imageId);
}
