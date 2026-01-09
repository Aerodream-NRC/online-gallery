package com.aerodream.image_service.Controller;

import com.aerodream.image_service.Service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public String uploadFile(MultipartFile file) throws IOException {
        return s3Service.uploadFile(file);
    }

    @DeleteMapping("/delete")
    public void deleteFile(String fileName, @RequestHeader("X-User-Id") Long userId) {
        s3Service.deleteFile(fileName, userId);
    }
}
