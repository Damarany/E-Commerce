package com.ARD.eCommerce.services.images;

import com.ARD.eCommerce.dtos.ImageDto;
import com.ARD.eCommerce.exceptions.FileLocationExeption;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.mapper.ImageMapper;
import com.ARD.eCommerce.model.Image;
import com.ARD.eCommerce.model.Product;
import com.ARD.eCommerce.repository.ImageRepo;
import com.ARD.eCommerce.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ImageRepo imageRepo;

    private final ImageMapper imageMapper;
    private final ProductService productService;

    private  Path fileLocation;

    @Value("${file.upload.base-path}")
    private String basePath = "";
    @Override
    public Image getImageById(Long id) {
        return imageRepo.findById(id).
                orElseThrow(()->new ResourceNotFoundException("no img found with id: " + id));
    }

    @Override
    public List<ImageDto> getImagesByProductId(Long productId) {
        List<Image> images = imageRepo.findimageByProductId(productId)
                .orElseThrow(()->new ResourceNotFoundException("no product with id: " + productId));
        List<ImageDto> imageDtos = imageMapper.imagesToImageDtos(images);
        return imageDtos;
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepo.findById(id).ifPresentOrElse(imageRepo::delete,()->{
            throw new ResourceNotFoundException("no img found with id: " + id);
        });
    }

    @Override
    public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
        this.fileLocation = Paths.get(basePath ).toAbsolutePath().normalize();
        List<ImageDto> savedImagesDto = new ArrayList<>();
//        try {
//                Files.createDirectory(this.fileLocation);
//        }catch (IOException e) {
//            throw new FileLocationExeption("this folder name already exist!");
//        }

        Product product = productService.getProductById(productId);
        for (MultipartFile file:files) {

            try {
            Image image = new Image();
            String uniqueFileName = UUID.randomUUID().toString() + file.getOriginalFilename();
            image.setFileName(uniqueFileName);
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes());
            image.setProduct(product);
            image.setSize(file.getSize());
                String buildDownloadUrl = "/api/v1/image/download/";
                String downloadUrl = buildDownloadUrl+image.getId();
            image.setDownloadUrl(downloadUrl);
                //add img to folder
//                Path targetLocation = this.fileLocation.resolve(file.getOriginalFilename());
//                System.out.println(targetLocation + "+++++++++++++++");
                //Files.copy(file.getInputStream(),targetLocation,StandardCopyOption.REPLACE_EXISTING);
                Image savedImage = imageRepo.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                Image save = imageRepo.save(savedImage);
                ImageDto imageDto = imageMapper.imageToImageDto(save);
                savedImagesDto.add(imageDto);
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }

        return savedImagesDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        String uniqueFileName = UUID.randomUUID().toString() + file.getOriginalFilename();
        try {
            image.setFileName(uniqueFileName);
            image.setFileType(file.getContentType());
            image.setImage(file.getBytes());
            imageRepo.save(image);
        } catch (IOException | RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}










