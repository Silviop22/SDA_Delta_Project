package com.ecommerce.ecommerce;

import com.ecommerce.ecommerce.config.AppConstants;
import com.ecommerce.ecommerce.model.entity.Role;
import com.ecommerce.ecommerce.repository.RoleRepo;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@SecurityScheme(name = "e-commerce", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class EcommerceApplication implements CommandLineRunner {


	@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {

		SpringApplication.run(EcommerceApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) {
		try {
			if (roleRepo.count() == 0) {  // Only initialize roles if none exist
				Role adminRole = new Role(AppConstants.ADMIN_ID, "ADMIN");
				Role userRole = new Role(AppConstants.USER_ID, "USER");
				List<Role> roles = List.of(adminRole, userRole);
				List<Role> savedRoles = roleRepo.saveAll(roles);
				savedRoles.forEach(role -> System.out.println("Saved Role: " + role));
			}
		} catch (Exception e) {
			// It's better to use a logger here
			System.out.println("Error initializing roles");
			e.printStackTrace();
		}
	}
}
