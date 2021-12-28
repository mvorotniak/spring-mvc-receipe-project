package com.mvoro.developer.springmvcrecipeproject.services;

import java.util.Set;

import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;

public interface RecipeService {

    Set<Recipe> getAllRecipes();
}
