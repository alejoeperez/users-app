package com.devs4j.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Devs4JController {

	@GetMapping
	public String helloWorld() {
		return "hello world!";
	}
}
