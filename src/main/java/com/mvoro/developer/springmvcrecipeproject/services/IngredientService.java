package com.mvoro.developer.springmvcrecipeproject.services;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findCommandByRecipeAndIngredientId(Long recipeId, Long id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
