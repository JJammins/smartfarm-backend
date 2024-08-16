package com.api.smartfarm.image;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;

@Service
public class FirebaseStorageService {

    @Autowired
    private ImageMetadataRepository imageMetadataRepository;
    
    @Autowired
    private TomatoSummaryService tomatoSummaryService;

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        
        BlobId blobId = BlobId.of("smartfarm-image-f87b3.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();
        
        Blob blob = storage.create(blobInfo, file.getBytes());

        // 메타데이터 저장
        ImageMetadata metadata = new ImageMetadata();
        metadata.setFileName(fileName);
        metadata.setFirebaseUrl(blob.getMediaLink());
        metadata.setContentType(file.getContentType());
        metadata.setSize(file.getSize());
        metadata.setUploadDate(LocalDate.now());

        imageMetadataRepository.save(metadata);
        
        tomatoSummaryService.updateTomatoSummary(fileName, LocalDate.now());

        return blob.getMediaLink();
    }

    public byte[] getImage(String fileName) {
        ImageMetadata metadata = imageMetadataRepository.findByFileName(fileName);
        if (metadata == null) {
            throw new RuntimeException("Image not found");
        }

        Storage storage = StorageClient.getInstance().bucket().getStorage();
        Blob blob = storage.get(BlobId.of("smartfarm-image-f87b3.appspot.com", fileName));
        return blob.getContent();
    }
}