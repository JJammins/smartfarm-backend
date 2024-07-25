package com.api.smartfarm.image;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, Long> {
    ImageMetadata findByFileName(String fileName);
//    List<ImageMetadata> findByUploadDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}