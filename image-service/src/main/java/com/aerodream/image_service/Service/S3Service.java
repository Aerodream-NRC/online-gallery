package com.aerodream.image_service.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    @Transactional
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        File temp = convertMultipartToFile(file);

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, temp));

        temp.delete();

        return fileName;
    }

    public String getFileUrl(@NonNull String fileName) {
        return amazonS3.generatePresignedUrl(
                new GeneratePresignedUrlRequest(bucketName, fileName)
        ).toString();
    }

    public void deleteFile(@NonNull String fileName, @NonNull Long userId) {
        //TODO Удалить если юзер является админом
        amazonS3.deleteObject(bucketName, fileName);
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File converted = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream outputStream = new FileOutputStream(converted);
        outputStream.write(file.getBytes());
        outputStream.close();
        return converted;
    }
}
