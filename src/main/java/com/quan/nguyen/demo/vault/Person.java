package com.quan.nguyen.demo.vault;

import org.springframework.data.annotation.Id;
import org.springframework.vault.repository.mapping.Secret;

import lombok.Data;

@Secret("person")
@Data
public class Person {
    @Id
	private String id;

	private String firstname;
	private String lastname;
	private String phone;
}