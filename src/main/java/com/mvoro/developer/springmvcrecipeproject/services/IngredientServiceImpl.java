package com.mvoro.developer.springmvcrecipeproject.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvoro.developer.springmvcrecipeproject.commands.IngredientCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.RecipeCommand;
import com.mvoro.developer.springmvcrecipeproject.commands.UnitOfMeasureCommand;
import com.mvoro.developer.springmvcrecipeproject.domain.Ingredient;
import com.mvoro.developer.springmvcrecipeproject.repositories.IngredientRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    private final RecipeService recipeService;

    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientServiceImpl(
        IngredientRepository ingredientRepository,
        RecipeService recipeService,
        UnitOfMeasureService unitOfMeasureService
    ) {
        this.ingredientRepository = ingredientRepository;
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
    public IngredientCommand saveIngredientCommand(final IngredientCommand ingredientCommand) {
        RecipeCommand recipeCommand = recipeService.findCommandById(ingredientCommand.getRecipeId());
        Optional<IngredientCommand> ingredientCommandOptional = findIngredientCommandById(recipeCommand, ingredientCommand.getId());

        updateOrAddIngredient(recipeCommand, ingredientCommandOptional, ingredientCommand);

        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return findIngredientCommandById(savedRecipeCommand, ingredientCommand.getId())
            .orElse(findIngredientCommandByAttributes(savedRecipeCommand, ingredientCommand.getDescription(), ingredientCommand.getAmount(),
                ingredientCommand.getUnitOfMeasureCommand().getId()));
    }

    @Override
    public void deleteByRecipeAndIngredientId(Long recipeId, Long id) {
        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(id)
            .filter(ing -> ing.getRecipe().getId().equals(recipeId));

        if (ingredientOptional.isPresent()) {
            ingredientRepository.deleteById(id);

            log.debug("Deleted ingredient with id {}", id);
        } else {
            log.warn("Unable to delete ingredient with id {}. It doesn't exist in recipe with id {}", id, recipeId);
        }
    }

    private Optional<IngredientCommand> findIngredientCommandById(final RecipeCommand recipeCommand, final Long id) {
        return recipeCommand.getIngredientCommands().stream()
            .filter(command -> command.getId().equals(id))
            .findFirst();
    }

    private IngredientCommand findIngredientCommandByAttributes(final RecipeCommand recipeCommand, final String description,
        final BigDecimal amount, final Long uomId) {

        return recipeCommand.getIngredientCommands().stream()
            .filter(ingredientCommand -> ingredientCommand.getDescription().equals(description))
            .filter(ingredientCommand -> ingredientCommand.getAmount().equals(amount))
            .filter(ingredientCommand -> ingredientCommand.getUnitOfMeasureCommand().getId().equals(uomId))
            .findFirst()
            .orElse(null);
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
