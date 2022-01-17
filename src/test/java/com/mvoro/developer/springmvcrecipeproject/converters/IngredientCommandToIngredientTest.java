package com.mvoro.developer.springmvcrecipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Ingredient;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientCommandToIngredientTest {

    @Mock
    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    @InjectMocks
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    private UnitOfMeasureCommand unitOfMeasureCommand;

    private IngredientCommand ingredientCommand;

    private UnitOfMeasure unitOfMeasure;

    @BeforeEach
    void setUp() {
        unitOfMeasureCommand = new UnitOfMeasureCommand();

        ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);
        ingredientCommand.setDescription("Baking powder");
        ingredientCommand.setUnitOfMeasureCommand(unitOfMeasureCommand);

        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
    }

    @Test
    void convert() {
        when(unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand)).thenReturn(unitOfMeasure);

        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);

        assertNotNull(ingredient);
        assertEquals(ingredientCommand.getDescription(), ingredient.getDescription());
    }
}