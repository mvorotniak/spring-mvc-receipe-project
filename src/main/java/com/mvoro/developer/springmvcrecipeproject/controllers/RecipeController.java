package com.mvoro.developer.springmvcrecipeproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.exceptions.NotFoundException;
import com.mvoro.developer.springmvcrecipeproject.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/recipes")
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}/show")
    public String showRecipe(@PathVariable final Long id, final Model model) {
        log.info("Showing recipe by id page...");
        model.addAttribute("recipe", recipeService.findById(id));

        return "recipe/recipe";
    }

    @GetMapping("/new")
    public String createNewRecipe(final Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    /**
     * Saves a recipe if there is no such recipe in the DB. If there is - updates the existing one.
     * @param recipeCommand model attribute populated with data from a form submitted to the /recipe endpoint
     * @return redirects to a page that shows the recently created recipe
     */
    @PostMapping("/recipe")
    public String saveOrUpdateRecipe(@ModelAttribute final RecipeCommand recipeCommand) {
        log.info("Creating new recipe with title '{}'...", recipeCommand.getDescription());
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipes/" + savedRecipeCommand.getId() + "/show";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable final Long id, final Model model) {
        log.info("Updating recipe with id {}...", id);
        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "recipe/recipeform";
    }

    @GetMapping("{id}/delete")
    public String deleteRecipe(@PathVariable final Long id) {
        log.info("Deleting recipe with id {}...", id);
        recipeService.deleteById(id);

        // Redirect to the main page
        return "redirect:/";
    }

}
