package com.api.smartfarm.image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageMetadataRepository extends JpaRepository<ImageMetadata, Long> {
    ImageMetadata findByFileName(String fileName);
}