package com.mvoro.developer.springmvcrecipeproject.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.mvoro.developer.springmvcrecipeproject.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mvoro.developer.springmvcrecipeproject.converters.RecipeCommandToRecipe;
import com.mvoro.developer.springmvcrecipeproject.converters.RecipeToRecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.repositories.RecipeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Mocks initialization with JUnit 5
@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Test
    void getAllRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());

        when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> result = recipeService.getAllRecipes();

        assertEquals(1, result.size());
        verify(recipeRepository).findAll();
    }

    @Test
    void findById() {
        Long id = 1L;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));

        Recipe result = recipeService.findById(id);

        assertEquals(id, result.getId());
        verify(recipeRepository).findById(id);
    }

    @Test
    void findById_notFound() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> recipeService.findById(1L));
    }
}