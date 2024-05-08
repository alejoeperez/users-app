package com.devs4j.users.faker;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

@Component
public class FakerBuild {

	@Bean
	public Faker getFaker() {
		return new Faker();
	}
}
