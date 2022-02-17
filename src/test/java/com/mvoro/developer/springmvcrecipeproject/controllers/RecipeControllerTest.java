package com.mvoro.developer.springmvcrecipeproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    private static final Long ID = 1L;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController controller;

    private MockMvc mockMvc;

    private Recipe recipe;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        recipe = new Recipe();
        recipe.setId(ID);
    }

    @Test
    void showRecipe() throws Exception {
        when(recipeService.findById(ID)).thenReturn(recipe);

        mockMvc.perform(get("/recipes/" + ID + "/show"))
            .andExpect(status().isOk())
            .andExpect(model().attribute("recipe", recipe))
            .andExpect(view().name("recipe"));

        verify(recipeService).findById(ID);
    }

    @Test
    void createNewRecipe() throws Exception {
        mockMvc.perform(get("/recipes/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("recipe"))
            .andExpect(view().name("recipeform"));
    }

    @Test
    void saveOrUpdateRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);

        mockMvc.perform(post("/recipes/recipe"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipes/" + recipeCommand.getId() + "/show"));
    }

    @Test
    void updateRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(2L);

        when(recipeService.findCommandById(any())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipes/" + recipeCommand.getId() + "/update"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("recipe"))
            .andExpect(view().name("recipeform"));
    }
}