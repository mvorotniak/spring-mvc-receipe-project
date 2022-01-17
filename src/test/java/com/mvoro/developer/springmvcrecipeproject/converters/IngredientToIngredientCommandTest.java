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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientToIngredientCommandTest {

    @Mock
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @InjectMocks
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    private UnitOfMeasure unitOfMeasure;

    private Ingredient ingredient;

    private UnitOfMeasureCommand unitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        unitOfMeasure = new UnitOfMeasure();

        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setDescription("Baking powder");
        ingredient.setUnitOfMeasure(unitOfMeasure);

        unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(1L);
    }

    @Test
    void convert() {
        when(unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)).thenReturn(unitOfMeasureCommand);

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        assertNotNull(ingredientCommand);
        assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
        assertEquals(unitOfMeasureCommand, ingredientCommand.getUnitOfMeasureCommand());
    }
}