package com.mvoro.developer.springmvcrecipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mvoro.developer.springmvcrecipeproject.services.ImageService;
import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ImageController {

    private final ImageService imageService;

    private final RecipeService recipeService;

    public ImageController(
        ImageService imageService,
        RecipeService recipeService
    ) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipes/{id}/image")
    public String showImageUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", this.recipeService.findCommandById(id));

        return "recipe/imageUploadForm";
    }

    @PostMapping("recipes/{id}/image")
    public String saveImage(@PathVariable Long id, @RequestParam("imageFile") MultipartFile file) {
        imageService.uploadImageForRecipeId(id, file);

        return "redirect:/recipes/" + id + "/show";
    }
}
