package com.mbadady.simpleBankApp;

import com.mbadady.simpleBankApp.dao.AccountRepository;
import com.mbadady.simpleBankApp.dao.RoleRepository;
import com.mbadady.simpleBankApp.model.Account;
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
	private RoleRepository roleRepository;

	@Autowired
	private AccountRepository accountRepository;
	public static void main(String[] args) {
		SpringApplication.run(SimpleBankAppApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {


		// this is for initial population of roles
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

		//		This is to populate the initial account number

		Optional<Account> account = accountRepository.findByAccountNumber("0000000001");
		if(account.isEmpty()){
			Account initialAccountNumber = new Account();
			initialAccountNumber.setAccountNumber("0000000001");
			accountRepository.save(initialAccountNumber);
		}
	}

}
