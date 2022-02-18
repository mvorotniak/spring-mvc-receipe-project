package com.mvoro.developer.springmvcrecipeproject.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeService recipeService;

    public IngredientServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public IngredientCommand findCommandByRecipeAndIngredientId(Long recipeId, Long id) {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);

        Optional<IngredientCommand> ingredientCommandOptional = recipeCommand.getIngredientCommands().stream()
            .filter(ingredientCommand -> ingredientCommand.getId().equals(id))
            .findFirst();

        if (ingredientCommandOptional.isEmpty()) {
            throw new RuntimeException("Unable to find ingredient with id " + id + " in recipe with id " + recipeId);
        }

        return ingredientCommandOptional.get();
    }
}
