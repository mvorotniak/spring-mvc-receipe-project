package com.mvoro.developer.springmvcrecipeproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mvoro.developer.springmvcrecipeproject.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
}
