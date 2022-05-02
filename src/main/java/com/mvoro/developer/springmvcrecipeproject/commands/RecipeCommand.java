package com.mvoro.developer.springmvcrecipeproject.commands;

import java.util.HashSet;
import java.util.Set;

import com.mvoro.developer.springmvcrecipeproject.domain.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private Long id;

    private NoteCommand noteCommand;

    private Set<IngredientCommand> ingredientCommands = new HashSet<>();

    private Set<CategoryCommand> categoryCommands = new HashSet<>();

    private String description;

    private Integer prepTime;

    private Integer cookTime;

    private Integer servings;

    private String source;

    private String url;

    private String directions;

    private Difficulty difficulty;

    private Byte[] image;

}
