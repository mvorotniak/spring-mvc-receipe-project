package com.mvoro.developer.springmvcrecipeproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.mvoro.developer.springmvcrecipeproject.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
