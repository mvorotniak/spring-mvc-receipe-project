package com.mvoro.developer.springmvcrecipeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;

@SpringBootApplication(exclude = {H2ConsoleAutoConfiguration.class})
public class SpringMvcRecipeProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcRecipeProjectApplication.class, args);
    }

}
