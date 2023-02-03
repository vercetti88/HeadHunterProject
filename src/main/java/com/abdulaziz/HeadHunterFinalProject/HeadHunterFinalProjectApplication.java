package com.abdulaziz.HeadHunterFinalProject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class HeadHunterFinalProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeadHunterFinalProjectApplication.class, args);
	}

	@Bean
	public ModelMapper mapper(){
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}






}
