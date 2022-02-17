package com.mvoro.developer.springmvcrecipeproject.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mvoro.developer.springmvcrecipeproject.commands.CategoryCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    private CategoryToCategoryCommand categoryToCategoryCommand;

    private Category category;

    @BeforeEach
    void setUp() {
        categoryToCategoryCommand = new CategoryToCategoryCommand();

        category = new Category();
        category.setId(1L);
        category.setName("Breakfast");
    }

    @Test
    void convert() {
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);

        assertNotNull(categoryCommand);
        assertEquals(category.getName(), categoryCommand.getName());
    }

    @Test
    void convert_nullValue() {
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(null);

        assertNull(categoryCommand);
    }
}