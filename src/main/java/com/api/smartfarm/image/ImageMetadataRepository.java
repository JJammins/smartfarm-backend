package com.api.smartfarm.image;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, Long> {
    ImageMetadata findByFileName(String fileName);
    List<ImageMetadata> findByUploadDate(LocalDate date);
    List<ImageMetadata> findByUploadDateBetween(LocalDate startDate, LocalDate endDate);

//    List<ImageMetadata> findByUploadDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}