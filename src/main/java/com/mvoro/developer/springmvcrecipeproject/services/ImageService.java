package com.mvoro.developer.springmvcrecipeproject.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void uploadImageForRecipeId(Long id, MultipartFile file);
}
