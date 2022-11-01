package com.mvoro.developer.springmvcrecipeproject.bootstrap;

import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.mvoro.developer.springmvcrecipeproject.domain.Category;
import com.mvoro.developer.springmvcrecipeproject.domain.UnitOfMeasure;
import com.mvoro.developer.springmvcrecipeproject.repositories.CategoryRepository;
import com.mvoro.developer.springmvcrecipeproject.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile({"dev", "pro"})
public class RecipesInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    // Constructor injection is recommended over field injection
    public RecipesInitializer(
        CategoryRepository categoryRepository,
        UnitOfMeasureRepository unitOfMeasureRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Populating the DB in DEV/PRO environment...");
        if (!categoryRepository.findAll().iterator().hasNext()) {
            categoryRepository.saveAll(this.loadCategories());
        }

        if (!unitOfMeasureRepository.findAll().iterator().hasNext()) {
            unitOfMeasureRepository.saveAll(this.loadUom());
        }
        log.info("Finished populating the DB in DEV/PRO environment");
    }

    private Set<Category> loadCategories() {
        final Category mexican = new Category();
        mexican.setName("Mexican");
        final Category italian = new Category();
        mexican.setName("Italian");
        final Category french = new Category();
        mexican.setName("French");

        return Set.of(mexican, italian, french);
    }

    private Set<UnitOfMeasure> loadUom() {
        final UnitOfMeasure litres = new UnitOfMeasure();
        litres.setDescription("Litres");
        final UnitOfMeasure grams = new UnitOfMeasure();
        grams.setDescription("Grams");

        return Set.of(litres, grams);
    }

}
