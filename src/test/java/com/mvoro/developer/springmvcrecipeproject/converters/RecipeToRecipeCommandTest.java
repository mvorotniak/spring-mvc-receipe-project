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
class RecipeToRecipeCommandTest {

    @Mock
    private CategoryToCategoryCommand categoryToCategoryCommand;

    @Mock
    private NoteToNoteCommand noteToNoteCommand;

    @Mock
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @InjectMocks
    private RecipeToRecipeCommand recipeToRecipeCommand;

    private Note note;

    private Category category;

    private Ingredient ingredient;

    private Recipe recipe;

    private NoteCommand noteCommand;

    private CategoryCommand categoryCommand;

    private IngredientCommand ingredientCommand;

    @BeforeEach
    void setUp() {
        note = new Note();
        note.setId(1L);

        category = new Category();
        category.setId(2L);

        Set<Category> categories = new HashSet<>();
        categories.add(category);

        ingredient = new Ingredient();
        ingredient.setId(3L);

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setDescription("Lasagna");
        recipe.setNote(note);
        recipe.setCategories(categories);
        recipe.setIngredients(ingredients);

        noteCommand = new NoteCommand();
        noteCommand.setId(1L);

        categoryCommand = new CategoryCommand();
        categoryCommand.setId(2L);

        ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
    }

    @Test
    void convert() {
        when(noteToNoteCommand.convert(note)).thenReturn(noteCommand);
        when(categoryToCategoryCommand.convert(category)).thenReturn(categoryCommand);
        when(ingredientToIngredientCommand.convert(ingredient)).thenReturn(ingredientCommand);

        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        assertNotNull(recipeCommand);
        assertEquals(recipe.getDescription(), recipeCommand.getDescription());
        assertEquals(recipe.getNote().getId(), recipeCommand.getNoteCommand().getId());
        assertEquals(1, recipeCommand.getCategoryCommands().size());
        assertEquals(1, recipeCommand.getIngredientCommands().size());
    }
}