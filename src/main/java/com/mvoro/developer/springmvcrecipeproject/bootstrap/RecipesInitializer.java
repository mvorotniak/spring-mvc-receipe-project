package com.mvoro.developer.springmvcrecipeproject.bootstrap;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.mvoro.developer.springmvcrecipeproject.domain.Category;
import com.mvoro.developer.springmvcrecipeproject.domain.Difficulty;
import com.mvoro.developer.springmvcrecipeproject.domain.Ingredient;
import com.mvoro.developer.springmvcrecipeproject.domain.Note;
import com.mvoro.developer.springmvcrecipeproject.domain.Recipe;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;
import com.mvoro.developer.springmvcrecipeproject.repositories.CategoryRepository;
import com.mvoro.developer.springmvcrecipeproject.repositories.RecipeRepository;
import com.mvoro.developer.springmvcrecipeproject.repositories.UnitOfMeasureRepository;

@Component
public class RecipesInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recipeRepository.saveAll(getRecipes());
    }

    private Set<Recipe> getRecipes() {
        Category breakfast =  categoryRepository.findByName("Breakfast")
            .orElseThrow(() -> new RuntimeException("Unable to find category 'Breakfast'"));

        UnitOfMeasure teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon")
            .orElseThrow(() -> new RuntimeException("Unable to find unit of measure 'Teaspoon'"));
        UnitOfMeasure tablespoon = unitOfMeasureRepository.findByDescription("Tablespoon")
            .orElseThrow(() -> new RuntimeException("Unable to find unit of measure 'Tablespoon'"));
        UnitOfMeasure cup = unitOfMeasureRepository.findByDescription("Cup")
            .orElseThrow(() -> new RuntimeException("Unable to find unit of measure 'Cup'"));
        UnitOfMeasure can = unitOfMeasureRepository.findByDescription("Can")
            .orElseThrow(() -> new RuntimeException("Unable to find unit of measure 'Can'"));

        Ingredient flour = new Ingredient();
        flour.setDescription("Flour");
        flour.setAmount(BigDecimal.valueOf(2));
        flour.setUnitOfMeasure(teaspoon);

        Ingredient spice = new Ingredient();
        spice.setDescription("Pumpkin pie spice");
        spice.setAmount(BigDecimal.valueOf(1));
        spice.setUnitOfMeasure(teaspoon);

        Ingredient powder = new Ingredient();
        powder.setDescription("Baking powder");
        powder.setAmount(BigDecimal.valueOf(1.5));
        powder.setUnitOfMeasure(tablespoon);

        Recipe pumpkin = new Recipe();
        pumpkin.setDescription("Pumpkin Chocolate Chip Muffins");
        pumpkin.setPrepTime(20);
        pumpkin.setCookTime(20);
        pumpkin.setServings(12);
        pumpkin.setSource("simplyrecipes.com");
        pumpkin.setUrl("https://www.simplyrecipes.com/pumpkin-chocolate-chip-muffins-recipe-5206559");
        pumpkin.setDirections("Bakery");
        pumpkin.setDifficulty(Difficulty.MODERATE);
        pumpkin.getIngredients().add(flour);
        pumpkin.getIngredients().add(spice);
        pumpkin.getIngredients().add(powder);

        flour.setRecipe(pumpkin);
        spice.setRecipe(pumpkin);
        powder.setRecipe(pumpkin);

        Ingredient cantaloupe = new Ingredient();
        cantaloupe.setDescription("Cantaloupe");
        cantaloupe.setAmount(BigDecimal.valueOf(0.5));
        cantaloupe.setUnitOfMeasure(cup);

        Ingredient yogurt = new Ingredient();
        yogurt.setDescription("Greek yogurt");
        yogurt.setAmount(BigDecimal.valueOf(0.25));
        yogurt.setUnitOfMeasure(cup);

        Ingredient juice = new Ingredient();
        juice.setDescription("Pineapple juice");
        juice.setAmount(BigDecimal.valueOf(0.5));
        juice.setUnitOfMeasure(tablespoon);

        Recipe smoothie = new Recipe();
        smoothie.setDescription("Cantaloupe Smoothie");
        smoothie.setPrepTime(5);
        smoothie.setCookTime(5);
        smoothie.setServings(1);
        smoothie.setSource("simplyrecipes.com");
        smoothie.setUrl("https://www.simplyrecipes.com/cantaloupe-smoothie-recipe-5204176");
        smoothie.setDirections("Drinks");
        smoothie.setDifficulty(Difficulty.EASY);
        smoothie.getIngredients().add(cantaloupe);
        smoothie.getIngredients().add(yogurt);
        smoothie.getIngredients().add(juice);

        cantaloupe.setRecipe(smoothie);
        yogurt.setRecipe(smoothie);
        juice.setRecipe(smoothie);

        // Recipe-Category Many to Many relationship
        pumpkin.getCategories().add(breakfast);
        smoothie.getCategories().add(breakfast);

        Note pumpkinNote = new Note();
        pumpkinNote.setNote("These Pumpkin Chocolate Chip Muffins combine cozy pumpkin spice with pockets of rich chocolate. They’re an easy way to add some much-needed spiced comfort to chilly mornings.");
        pumpkinNote.setRecipe(pumpkin);
        pumpkin.setNote(pumpkinNote);

        Note smoothieNote = new Note();
        smoothieNote.setNote("Blend up a big glass of this refreshing Cantaloupe Smoothie for a quick cool down. Ripe pieces of juicy melon make the perfect light and sweet base for the chilly beverage.");
        smoothieNote.setRecipe(smoothie);
        smoothie.setNote(smoothieNote);

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(pumpkin);
        recipes.add(smoothie);

        return recipes;
    }
}
