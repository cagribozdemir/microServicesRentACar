package com.kodlamaio.inventoryServer.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.inventory.brand.BrandUpdatedEvent;
import com.kodlamaio.common.events.inventory.car.CarCreatedEvent;
import com.kodlamaio.common.events.inventory.car.CarDeletedEvent;
import com.kodlamaio.common.events.inventory.car.CarUpdatedEvent;
import com.kodlamaio.common.events.inventory.model.ModelUpdatedEvent;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(InventoryProducer.class);

	private NewTopic topic;
	
	private KafkaTemplate<String, CarCreatedEvent> kafkaTemplateCarCreated;
	private KafkaTemplate<String, CarDeletedEvent> kafkaTempleteCarDeleted;
	private KafkaTemplate<String, CarUpdatedEvent> kafkaTempleteCarUpdated;
	private KafkaTemplate<String, BrandUpdatedEvent> kafkaTempleteBrandUpdated;
	private KafkaTemplate<String, ModelUpdatedEvent> kafkaTempleteModelUpdated;

	public void sendMessage(CarCreatedEvent carCreatedEvent) {
		LOGGER.info(String.format("Car created event => %s", carCreatedEvent.toString()));
		
		Message<CarCreatedEvent> message = MessageBuilder
				.withPayload(carCreatedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();		
		kafkaTemplateCarCreated.send(message);
	}

	public void sendMessage(CarDeletedEvent carDeletedEvent) {
		LOGGER.info(String.format("Car deleted event => %s", carDeletedEvent.toString()));
		
		Message<CarDeletedEvent> message = MessageBuilder
				.withPayload(carDeletedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();		
		kafkaTempleteCarDeleted.send(message);
	}
	
	public void sendMessage(CarUpdatedEvent carUpdatedEvent) {
		LOGGER.info(String.format("Car update event => %s", carUpdatedEvent.toString()));
		
		Message<CarUpdatedEvent> message = MessageBuilder
				.withPayload(carUpdatedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();		
		kafkaTempleteCarUpdated.send(message);
	}
	
	public void sendMessage(BrandUpdatedEvent brandUpdatedEvent) {
		LOGGER.info(String.format("Brand update event => %s", brandUpdatedEvent.toString()));
		
		Message<BrandUpdatedEvent> message = MessageBuilder
				.withPayload(brandUpdatedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();		
		kafkaTempleteBrandUpdated.send(message);
	}
	
	public void sendMessage(ModelUpdatedEvent modelUpdatedEvent) {
		LOGGER.info(String.format("Model update event => %s", modelUpdatedEvent.toString()));
		
		Message<ModelUpdatedEvent> message = MessageBuilder
				.withPayload(modelUpdatedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();		
		kafkaTempleteModelUpdated.send(message);
	}
}
