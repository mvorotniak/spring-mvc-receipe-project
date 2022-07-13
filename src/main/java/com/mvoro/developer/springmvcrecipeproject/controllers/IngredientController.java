package com.mvoro.developer.springmvcrecipeproject.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String listIngredientsByRecipe(@PathVariable final Long id, final Model model) {
        final RecipeCommand recipeCommand = recipeService.findCommandById(id);
        model.addAttribute("recipe", recipeCommand);

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{id}/show")
    public String showIngredient(@PathVariable("recipe_id") final Long recipeId, @PathVariable final Long id,
        final Model model) {

        model.addAttribute("ingredient", ingredientService.findCommandByRecipeAndIngredientId(recipeId, id));

        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable("recipe_id") final Long recipeId, @PathVariable final Long id,
        final Model model) {

        final IngredientCommand ingredientCommand = ingredientService.findCommandByRecipeAndIngredientId(recipeId, id);
        ingredientCommand.setRecipeId(recipeId);
        final Set<UnitOfMeasureCommand> unitOfMeasureCommandSet = unitOfMeasureService.findAllCommand();

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureCommandSet);

        return "recipe/ingredient/form";
    }

    @PostMapping("/recipe/{id}/ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute("ingredient") @Valid final IngredientCommand ingredientCommand,
        final BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "recipe/ingredient/form";
        }

        final IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

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
