package com.devs4j.users.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devs4j.users.model.User;
import com.github.javafaker.Faker;

@Service
public class UserService {

	@Autowired
	private Faker faker;
	
	private List<User> users = new ArrayList<>();
	
	@PostConstruct
	public void init() {
		for (int i = 0; i < 100; i++) {			
			users.add(new User(faker.funnyName().name(), faker.name().username(),faker.dragonBall().character()));
		}
	}
	
	public List<User> getUsers(){
		return users;
	}
}
