package com.mvoro.developer.springmvcrecipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    private UnitOfMeasure unitOfMeasure;

    @BeforeEach
    void setUp() {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

        unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(1L);
        unitOfMeasure.setDescription("Tablespoon");
    }

    @Test
    void convert() {
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);

        assertNotNull(unitOfMeasureCommand);
        assertEquals(unitOfMeasure.getDescription(), unitOfMeasureCommand.getDescription());
    }
}