package com.security.practice.demo;

import com.security.practice.demo.domain.Role;
import com.security.practice.demo.domain.User;
import com.security.practice.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return arg -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "1111", "1111", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "2222", "2222", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "3333", "3333", "1234", new ArrayList<>()));
			userService.saveUser(new User(null, "4444", "4444", "1234", new ArrayList<>()));

			userService.addRoleToUser("1111", "ROLE_USER");
			userService.addRoleToUser("1111", "ROLE_ADMIN");
			userService.addRoleToUser("2222", "ROLE_MANAGER");
			userService.addRoleToUser("3333", "ROLE_ADMIN");
			userService.addRoleToUser("3333", "ROLE_SUPER_ADMIN");
			userService.addRoleToUser("4444", "ROLE_SUPER_ADMIN");
		};
	}
}
