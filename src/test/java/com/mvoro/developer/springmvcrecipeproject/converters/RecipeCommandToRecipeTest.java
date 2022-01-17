package com.mvoro.developer.springmvcrecipeproject.converters;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mvoro.developer.springmvcrecipeproject.commands.CategoryCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.NoteCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Category;
import com.mvoro.developer.springmvcrecipeproject.domain.Ingredient;
import com.mvoro.developer.springmvcrecipeproject.domain.Note;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeCommandToRecipeTest {

    @Mock
    private CategoryCommandToCategory categoryCommandToCategory;

    @Mock
    private NoteCommandToNote noteCommandToNote;

    @Mock
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @InjectMocks
    private RecipeCommandToRecipe recipeCommandToRecipe;

    private NoteCommand noteCommand;

    private CategoryCommand categoryCommand;

    private IngredientCommand ingredientCommand;

    private RecipeCommand recipeCommand;

    private Note note;

    private Category category;

    private Ingredient ingredient;

    @BeforeEach
    void setUp() {
        noteCommand = new NoteCommand();
        noteCommand.setId(1L);

        categoryCommand = new CategoryCommand();
        categoryCommand.setId(2L);

        Set<CategoryCommand> categoryCommands = new HashSet<>();
        categoryCommands.add(categoryCommand);

        ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);

        Set<IngredientCommand> ingredientCommands = new HashSet<>();
        ingredientCommands.add(ingredientCommand);

        recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        recipeCommand.setDescription("Lasagna");
        recipeCommand.setNoteCommand(noteCommand);
        recipeCommand.setCategoryCommands(categoryCommands);
        recipeCommand.setIngredientCommands(ingredientCommands);

        note = new Note();
        note.setId(1L);

        category = new Category();
        category.setId(2L);

        ingredient = new Ingredient();
        ingredient.setId(3L);
    }

    @Test
    void convert() {
        when(noteCommandToNote.convert(noteCommand)).thenReturn(note);
        when(categoryCommandToCategory.convert(categoryCommand)).thenReturn(category);
        when(ingredientCommandToIngredient.convert(ingredientCommand)).thenReturn(ingredient);

        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        assertNotNull(recipe);
        assertEquals(recipeCommand.getDescription(), recipe.getDescription());
        assertEquals(recipeCommand.getNoteCommand().getId(), recipe.getNote().getId());
        assertEquals(1, recipe.getCategories().size());
        assertEquals(1, recipe.getIngredients().size());
    }
}