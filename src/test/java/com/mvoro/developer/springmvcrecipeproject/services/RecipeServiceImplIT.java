package com.mvoro.developer.springmvcrecipeproject.services;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.converters.RecipeToRecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.repositories.RecipeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RecipeServiceImplIT {

    private static final String NEW_DESCRIPTION = "Updated Description";

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    // Without the Transactional annotation it throws a LazyInitializationException
    // Test method is surrounded by an overarching Spring transaction. This transaction will be rolled back at the end of the test method regardless of it's outcome.
    @Transactional
    void saveRecipeCommand() {
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe = recipes.iterator().next();

        recipe.setDescription(NEW_DESCRIPTION);
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        assertNotNull(savedRecipeCommand);
        assertEquals(recipe.getId(), savedRecipeCommand.getId());
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(recipe.getCategories().size(), savedRecipeCommand.getCategoryCommands().size());
        assertEquals(recipe.getIngredients().size(), savedRecipeCommand.getIngredientCommands().size());
    }
}