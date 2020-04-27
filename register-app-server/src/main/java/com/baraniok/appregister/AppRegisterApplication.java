package com.baraniok.appregister;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.baraniok.appregister.data.UserRepository;
import com.baraniok.appregister.model.User;

@SpringBootApplication
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AppRegisterApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(AppRegisterApplication.class, args);
	}
	
	
	@Bean
	public CommandLineRunner dataLoader(UserRepository repo) {
		return args -> {
			repo.save(new User("Jacek", "Password1"));
			repo.save(new User("Barbara", "Password1"));
		};
	}
		
}
