package com.mvoro.developer.springmvcrecipeproject.repositories;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;

import static org.junit.jupiter.api.Assertions.*;

/**
 * We use the @ExtendWith annotation to tell JUnit 5 to enable Spring support.
 *
 * To test Spring Data JPA repositories, or any other JPA-related components for that matter,
 * Spring Boot provides the @DataJpaTest annotation.
 * We can just add it to our unit test and it will set up a Spring application context.
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByDescription() {
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", teaspoon.get().getDescription());
    }
}