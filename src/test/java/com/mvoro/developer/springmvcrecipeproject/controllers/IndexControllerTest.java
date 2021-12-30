package com.mvoro.developer.springmvcrecipeproject.controllers;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
    void mockMvc() throws Exception {
        // Easy way to test an endpoint without creating an Integration test
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        // Verify that by calling the `index` endpoint we obtain a 200 OK response code
        // and a view called `index`
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
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