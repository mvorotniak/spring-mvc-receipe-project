package com.mvoro.developer.springmvcrecipeproject.controllers;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    private IndexController indexController;

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        indexController = new IndexController(recipeService);
    }

    @Test
    void index() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        String response = indexController.index(model);

        assertEquals("index", response);
        verify(recipeService, times(1)).getAllRecipes();
        verify(model, times(1)).addAttribute("recipes", recipes);
    }
}