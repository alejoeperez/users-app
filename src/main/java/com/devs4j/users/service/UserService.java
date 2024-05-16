package com.devs4j.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.devs4j.users.model.User;
import com.github.javafaker.Faker;

@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private Faker faker;

	private List<User> users = new ArrayList<>();

	@PostConstruct
	public void init() {
		log.info("Post construct: Initializaing the \"database\"");
		users = IntStream.range(0, 100).mapToObj(
				x -> new User(faker.funnyName().name(), faker.name().username(), faker.dragonBall().character()))
				.collect(Collectors.toList());
	}

	public List<User> getUsers() {
		log.info("Starting getUsers, users list size: [{}]", users.size());
		return users;
	}

	public List<User> getByStartWith(String text) {
		log.info("Starting getByStartWith");
		Optional<String> textOp = Optional.ofNullable(text);
		log.info("Filter: {}", text);
		return users.stream().filter(x -> {
											String filter = textOp.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"The parameter \"text\" is null"));
											if(!filter.equals("")) {
												return x.getUsername().startsWith(filter);
											}
											else {
												throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The parameter \"text\" is empty");
											}
										})
								.collect(Collectors.toList());
	}

	public List<User> getByContains(String text) {
		log.info("Starting getByContains");
		Optional<String> textOp = Optional.ofNullable(text);
		log.info("Filter: {}", text);
		return users.stream().filter(x -> {
											String filter = textOp.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"The parameter \"text\" is null"));
											if(!filter.equals("")) {
												return x.getUsername().contains(filter);
											}
											else {
												throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The parameter \"text\" is empty");
											}
										})
							.collect(Collectors.toList());
	}

	public User getUserByUsername(String username) {
		// filter the list of users by username. Once it founds any return the User,
		// but findAny() retruns an optional object
		// orElseThrow allows return an exception, in this case Response Status
		// Exception indicating that the uses is not found
		log.info("Starting getUserByUsername, username: [{}]", username);
		return users.stream().filter(x -> x.getUsername().equals(username)).findAny().orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User %s not found", username)));
	}

	public User createUser(User user) {
		log.info("Starting createUser");
		Optional<String> usernameOp = Optional.ofNullable(user.getUsername());
		String username = usernameOp
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "The username is null"));
		log.info("New user info: {}", user.toString());
		if (users.stream().anyMatch(x -> x.getUsername().equals(username))) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					String.format("The user with username [ %s ] already exists", username));
		}
		users.add(user);
		return user;
	}

	public User updateUser(User user, String username) {
		log.info("Starting updateUser, username: [{}]", username);
		User userToBeUpdated = getUserByUsername(username);
		userToBeUpdated.setNickName(user.getNickName());
		userToBeUpdated.setPassword(user.getPassword());
		return userToBeUpdated;
	}

	public void deleteUser(String username) {
		log.info("Starting deleteUser, username: [{}]", username);
		User userToBeDeleted = getUserByUsername(username);
		users.remove(userToBeDeleted);
	}

}
