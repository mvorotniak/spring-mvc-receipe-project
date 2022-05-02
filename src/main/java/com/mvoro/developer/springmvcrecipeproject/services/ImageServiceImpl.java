package com.mvoro.developer.springmvcrecipeproject.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public void uploadImageForRecipeId(final Long id, final MultipartFile file) {
        log.info("Received file for uploading...");
    }
}
