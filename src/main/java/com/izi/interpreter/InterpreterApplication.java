package com.izi.interpreter;

import com.izi.interpreter.repositories.LanguageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(LanguageRepository.class)
public class InterpreterApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterpreterApplication.class, args);
	}

}
