package com.FinFlow.app.config;

import me.paulschwarz.springdotenv.DotenvPropertySource;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class DotenvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // Load .env file using spring-dotenv
        DotenvPropertySource.addToEnvironment(applicationContext.getEnvironment());
    }
}