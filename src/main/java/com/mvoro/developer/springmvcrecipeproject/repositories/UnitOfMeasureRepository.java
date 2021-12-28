package com.mvoro.developer.springmvcrecipeproject.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

    // Spring Data JPA will create an SQL query based on the finder method and execute the query for us
    Optional<UnitOfMeasure> findByDescription(String description);
}
