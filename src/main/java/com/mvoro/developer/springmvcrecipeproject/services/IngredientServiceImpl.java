package com.mvoro.developer.springmvcrecipeproject.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeService recipeService;

    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientServiceImpl(
        RecipeService recipeService,
        UnitOfMeasureService unitOfMeasureService
    ) {
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @Override
    public IngredientCommand findCommandByRecipeAndIngredientId(Long recipeId, Long id) {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);

        Optional<IngredientCommand> ingredientCommandOptional = findIngredientCommandById(recipeCommand, id);

        if (ingredientCommandOptional.isEmpty()) {
            throw new RuntimeException("Unable to find ingredient with id " + id + " in recipe with id " + recipeId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        RecipeCommand recipeCommand = recipeService.findCommandById(ingredientCommand.getRecipeId());
        Optional<IngredientCommand> ingredientCommandOptional = findIngredientCommandById(recipeCommand, ingredientCommand.getId());

        updateOrAddIngredient(recipeCommand, ingredientCommandOptional, ingredientCommand);

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return findIngredientCommandById(savedRecipeCommand, ingredientCommand.getId()).get();
    }

    private Optional<IngredientCommand> findIngredientCommandById(RecipeCommand recipeCommand, Long id) {
        return recipeCommand.getIngredientCommands().stream()
            .filter(command -> command.getId().equals(id))
            .findFirst();
    }

    private void updateOrAddIngredient(RecipeCommand recipeCommand,
        Optional<IngredientCommand> ingredientCommandOptional, IngredientCommand ingredientCommand) {

        if (ingredientCommandOptional.isPresent()) {
            updateIngredientCommand(ingredientCommandOptional.get(), ingredientCommand);
        } else {
            recipeCommand.getIngredientCommands().add(ingredientCommand);
        }
    }

    private void updateIngredientCommand(IngredientCommand fromIngredientCommand, IngredientCommand toIngredientCommand) {
        fromIngredientCommand.setDescription(toIngredientCommand.getDescription());
        fromIngredientCommand.setAmount(toIngredientCommand.getAmount());

        UnitOfMeasureCommand unitOfMeasureCommand =
            unitOfMeasureService.findCommandById(toIngredientCommand.getUnitOfMeasureCommand().getId());
        if (unitOfMeasureCommand != null) {
            fromIngredientCommand.setUnitOfMeasureCommand(unitOfMeasureCommand);
        }
    }
}
