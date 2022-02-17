package com.mvoro.developer.springmvcrecipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mvoro.developer.springmvcrecipeproject.commands.CategoryCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    private CategoryCommandToCategory categoryCommandToCategory;

    private CategoryCommand categoryCommand;

    @BeforeEach
    void setUp() {
        categoryCommandToCategory = new CategoryCommandToCategory();

        categoryCommand = new CategoryCommand();
        categoryCommand.setId(1L);
        categoryCommand.setName("Breakfast");
    }

    @Test
    void convert() {
        Category category = categoryCommandToCategory.convert(categoryCommand);

        assertNotNull(category);
        assertEquals(categoryCommand.getName(), category.getName());
    }

    @Test
    void convert_nullValue() {
        Category category = categoryCommandToCategory.convert(null);

        assertNull(category);
    }
}