package com.kodlamaio.inventoryServer;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kodlamaio.common.utilities.mapping.ModelMapperManager;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;

@SpringBootApplication
public class InvertoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvertoryServiceApplication.class, args);
	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ModelMapperService getModelMapperService(ModelMapper modelMapper) {
		return new ModelMapperManager(modelMapper);
	}
}
