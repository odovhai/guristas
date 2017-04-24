package ru.guristas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class InitializerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitializerApplication.class, args);
	}
}
