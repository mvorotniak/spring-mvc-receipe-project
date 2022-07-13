package com.mvoro.developer.springmvcrecipeproject.controllers;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.services.IngredientService;
import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;
import com.mvoro.developer.springmvcrecipeproject.services.UnitOfMeasureService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    private IngredientController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void listIngredientsByRecipe() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeService.findCommandById(any())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/" + 1L + "/ingredients"))
            .andExpect(model().attributeExists("recipe"))
            .andExpect(view().name("recipe/ingredient/list"));
    }

    @Test
    public void showIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);

        when(ingredientService.findCommandByRecipeAndIngredientId(any(), any())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/" + 1L + "/ingredient/" + 1L + "/show"))
            .andExpect(model().attributeExists("ingredient"))
            .andExpect(view().name("recipe/ingredient/show"));
    }

    @Test
    public void updateIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);

        UnitOfMeasureCommand uom1 = new UnitOfMeasureCommand();
        uom1.setId(1L);
        UnitOfMeasureCommand uom2 = new UnitOfMeasureCommand();
        uom2.setId(1L);

        Set<UnitOfMeasureCommand> uoms = new HashSet<>();
        uoms.add(uom1);
        uoms.add(uom2);

        when(ingredientService.findCommandByRecipeAndIngredientId(any(), any())).thenReturn(ingredientCommand);
        when(unitOfMeasureService.findAllCommand()).thenReturn(uoms);

        mockMvc.perform(get("/recipe/" + 1L + "/ingredient/" + 2L + "/update"))
            .andExpect(model().attributeExists("ingredient"))
            .andExpect(model().attributeExists("uoms"))
            .andExpect(view().name("recipe/ingredient/form"))
            .andExpect(status().isOk());
    }

    @Test
    public void saveOrUpdateIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        ingredientCommand.setRecipeId(1L);
        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);

        mockMvc.perform(post("/recipe/" + 1L + "/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "Ingredient description")
                .param("amount", "10"))
            .andExpect(status().is3xxRedirection());

        verify(ingredientService, times(1)).saveIngredientCommand(any());
    }
}