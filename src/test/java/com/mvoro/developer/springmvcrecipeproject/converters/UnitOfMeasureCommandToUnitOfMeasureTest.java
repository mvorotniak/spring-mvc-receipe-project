package com.mvoro.developer.springmvcrecipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    private UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    private UnitOfMeasureCommand unitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();

        unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(1L);
        unitOfMeasureCommand.setDescription("Tablespoon");
    }

    @Test
    void convert() {
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);

        assertNotNull(unitOfMeasure);
        assertEquals(unitOfMeasureCommand.getDescription(), unitOfMeasure.getDescription());
    }
}