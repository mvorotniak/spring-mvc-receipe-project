package com.mvoro.developer.springmvcrecipeproject.controllers;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.services.IngredientService;
import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;
import com.mvoro.developer.springmvcrecipeproject.services.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(
        RecipeService recipeService,
        IngredientService ingredientService,
        UnitOfMeasureService unitOfMeasureService
    ) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredientsByRecipe(@PathVariable Long id, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(id);
        model.addAttribute("recipe", recipeCommand);

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{id}/show")
    public String showIngredient(@PathVariable("recipe_id") Long recipeId, @PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.findCommandByRecipeAndIngredientId(recipeId, id));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable("recipe_id") Long recipeId, @PathVariable Long id, Model model) {
        IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeAndIngredientId(recipeId, id);
        ingredientCommand.setRecipeId(recipeId);
        Set<UnitOfMeasureCommand> unitOfMeasureCommandSet = unitOfMeasureService.findAllCommand();

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureCommandSet);

        return "recipe/ingredient/form";
    }

    @PostMapping("/recipe/{id}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }

    @GetMapping("/recipe/{id}/ingredient/new")
    public String createNewIngredient(@PathVariable Long id, Model model) {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(id);

        Set<UnitOfMeasureCommand> unitOfMeasureCommandSet = unitOfMeasureService.findAllCommand();

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureCommandSet);

        return "recipe/ingredient/form";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable("recipe_id") Long recipeId, @PathVariable Long id) {
        log.info("Deleting ingredient with id {}...", id);
        ingredientService.deleteByRecipeAndIngredientId(recipeId, id);

        // Redirect to list of ingredients
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}
