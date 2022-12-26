package com.example.workout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@ConfigurationProperties
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EnableJpaRepositories
public class WorkoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkoutApplication.class, args);
	}

}
