package com.ARD.eCommerce.controller;


import com.ARD.eCommerce.dtos.ImageDto;
import com.ARD.eCommerce.exceptions.ResourceNotFoundException;
import com.ARD.eCommerce.model.Image;
import com.ARD.eCommerce.response.ResponseAPI;
import com.ARD.eCommerce.services.images.ImageService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${request.api.point}/image")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseAPI> addImages(@RequestParam  Long productId, @RequestParam List<MultipartFile> images){
        try {
            List<ImageDto> imageDtos = imageService.saveImages(productId, images);
            return ResponseEntity.ok(new ResponseAPI("Upload success!", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseAPI("Upload failed!", e.getMessage()));
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Object> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage());
//                .getBytes(1, (int) image.getImage().length()));
//        InputStream inputStream = image.getImage().getBinaryStream();
//        InputStreamResource resource = new InputStreamResource(inputStream);
        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<ResponseAPI>updateImageById(@PathVariable Long imageId,@RequestBody MultipartFile file){
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null){
                imageService.updateImage(file,imageId);
                return ResponseEntity.ok(new ResponseAPI("updated successfully",null));
            }
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(exception.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseAPI("update failed",HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/delete/{imageId}")

    public ResponseEntity<ResponseAPI>deleteImgById(@PathVariable Long imageId){
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ResponseAPI("deleted successfully",null));
            }
        } catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseAPI(exception.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseAPI("delete failed",HttpStatus.INTERNAL_SERVER_ERROR));
    }


@GetMapping("/product/{productId}")
    public ResponseEntity<ResponseAPI>getImagesByProductId(@PathVariable Long productId){
    try {
        List<ImageDto> imageDtos = imageService.getImagesByProductId(productId);
        return ResponseEntity.ok(new ResponseAPI("done",imageDtos));
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ResponseAPI("failed",HttpStatus.NOT_FOUND));
    }
}









}












