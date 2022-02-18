package com.mvoro.developer.springmvcrecipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.services.IngredientService;
import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService,IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String listIngredientsByRecipe(@PathVariable Long id, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(id);
        model.addAttribute("recipe", recipeCommand);

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipe_id}/ingredient/{id}/show")
    public String showIngredient(@PathVariable("recipe_id") Long recipeId, @PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeAndIngredientId(recipeId, id));

        return "recipe/ingredient/show";
    }
}
