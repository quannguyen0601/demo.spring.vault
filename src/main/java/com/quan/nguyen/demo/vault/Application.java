package com.quan.nguyen.demo.vault;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.repository.configuration.EnableVaultRepositories;
import org.springframework.vault.support.VaultResponse;

import lombok.extern.log4j.Log4j2;

@SpringBootApplication
@EnableVaultRepositories
@Log4j2
public class Application {
	@Autowired
	private PersonRepository repository;

	@Autowired
	VaultTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	private void postConstruct() {

		System.out.println("##########################");

		Person person = new Person();
		person.setId("heisenberg");
		person.setFirstname("Walter");
		person.setLastname("White");
		person.setPhone("1234");

		//VaultResponse response = template.write("secret/person/heisenberg",person);
		//log.info("Vault >>>> {}",response);
		repository.save(person);
		//Optional<Person> loaded = repository.findById(person.getId());
		//log.info("Retrieved data {} from Vault via Repository", loaded.get());

		// Let's encrypt some data using the Transit backend.
		VaultTransitOperations transitOperations = template.opsForTransit();

		 // Encrypt a plain-text value
		 String ciphertext = transitOperations.encrypt("dev-key", person.getFirstname());

		 System.out.println("Encrypted value");
		 System.out.println("-------------------------------");
		 System.out.println(ciphertext);
		 System.out.println("-------------------------------");
		 System.out.println();
  	 

		System.out.println("##########################");
	}

}
