package com.example.s3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.file.Paths;

@Service
public class S3Service {
    @Autowired
    private S3Client s3Client;
    private final String bucketName = "your-bucket-name";

    public void uploadFile(String key, String filePath) {
        s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(),
                RequestBody.fromFile(new File(filePath)));
        System.out.println("File uploaded successfully!");
    }

    public void downloadFile(String key, String downloadPath) {
        s3Client.getObject(GetObjectRequest.builder().bucket(bucketName).key(key).build(),
                ResponseTransformer.toFile(Paths.get(downloadPath)));
        System.out.println("File downloaded successfully!");
    }

    public void deleteFile(String key) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
        System.out.println("File deleted successfully!");
    }

    public void listFiles() {
        ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();
        ListObjectsV2Response response = s3Client.listObjectsV2(request);
        System.out.println("Files in bucket:");
        response.contents().forEach(obj -> System.out.println("- " + obj.key()));
    }
}