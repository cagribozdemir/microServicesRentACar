package com.kodlamaio.filterService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.inventory.brand.BrandUpdatedEvent;
import com.kodlamaio.common.events.inventory.car.CarCreatedEvent;
import com.kodlamaio.common.events.inventory.car.CarDeletedEvent;
import com.kodlamaio.common.events.inventory.car.CarUpdatedEvent;
import com.kodlamaio.common.events.inventory.model.ModelUpdatedEvent;
import com.kodlamaio.filterService.business.abstracts.FilterService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryConsumer.class);
	private FilterService filterService;

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "createcar")
	public void consume(CarCreatedEvent event) {
		LOGGER.info(String.format("Car event received in filter service => %s", event.toString()));
		filterService.addCar(event);
		
	}
	
	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "updatecar")
	public void consume(CarUpdatedEvent event) {
		LOGGER.info(String.format("Car event received in filter service => %s", event.toString()));
		filterService.updateCar(event);
		
	}
	
	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "deletecar")
	public void consume(CarDeletedEvent event) {
		LOGGER.info(String.format("Car event received in filter service => %s", event.toString()));
		filterService.deleteCar(event);
		
	}
	
	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "updatebrand")
	public void consume(BrandUpdatedEvent event) {
		LOGGER.info(String.format("Brand event received in filter service => %s", event.toString()));
		filterService.updateBrand(event);
		
	}
	
	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "updatemodel")
	public void consume(ModelUpdatedEvent event) {
		LOGGER.info(String.format("Model event received in filter service => %s", event.toString()));
		filterService.updateModel(event);
		
	}
}
