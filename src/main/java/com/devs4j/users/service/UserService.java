package com.devs4j.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
			users.add(new User(faker.funnyName().name(), faker.name().username(), faker.dragonBall().character()));
		}
	}

	public List<User> getUsers() {
		return users;
	}
	
	public List<User> getByStartWith(String text){
		if(text != null && !text.isEmpty()) {			
			return users.stream().filter(x->x.getUsername().startsWith(text)).collect(Collectors.toList());
		}else {
			return users;
		}
	}
	
	public List<User> getByContains(String text){
		if(text != null && !text.isEmpty()) {			
			return users.stream().filter(x->x.getUsername().contains(text)).collect(Collectors.toList());
		}else {
			return users;
		}
	}

	public User getUserByUsername(String username) {
		//filter the list of users by username. Once it founds any return the User, 
		//but findAny() retruns an optional object
		//orElseThrow allows return an exception, in this case Response Status Exception indicating that the uses is not found
		return users.stream().filter(x -> x.getUsername().equals(username)).findAny()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("User %s not found", username)));
	}
	
	public User createUser(User user) {
		if(user.getUsername()==null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "The username is null");
		}
		if(users.stream().anyMatch(x-> x.getUsername().equals(user.getUsername()))) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("The user with username [ %s ] already exists", user.getUsername()));
		}
		users.add(user);
		return user;
	}
	
	public User updateUser(User user, String username) {
		User userToBeUpdated = getUserByUsername(username);
		userToBeUpdated.setNickName(user.getNickName());
		userToBeUpdated.setPassword(user.getPassword());
		return userToBeUpdated;
	}
	
	public void deleteUser(String username) {
		User userToBeDeleted = getUserByUsername(username);
		users.remove(userToBeDeleted);
	}
	
}
