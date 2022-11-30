package com.mbadady.simpleBankApp;

import com.mbadady.simpleBankApp.dao.RoleRepository;
import com.mbadady.simpleBankApp.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class SimpleBankAppApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SimpleBankAppApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
		Optional<Role> userRole = roleRepository.findByName("ROLE_USER");

		if(adminRole.isEmpty()){
			Role role = new Role();
			role.setName("ROLE_ADMIN");
			roleRepository.save(role);

		}

		if(userRole.isEmpty()){
			Role role = new Role();
			role.setName("ROLE_USER");
			roleRepository.save(role);
		}
	}

}
