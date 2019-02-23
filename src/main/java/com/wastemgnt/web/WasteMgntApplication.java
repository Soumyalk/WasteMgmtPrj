package com.wastemgnt.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.wastemgnt.service.services")
@EntityScan("com.wastemgnt.service.entity")
@ComponentScan({"com.wastemgnt.web"})
public class WasteMgntApplication {

	public static void main(String[] args) {

		SpringApplication.run(WasteMgntApplication.class, args);
	}

}
