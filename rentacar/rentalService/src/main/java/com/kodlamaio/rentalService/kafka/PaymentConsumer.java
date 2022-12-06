package com.kodlamaio.rentalService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.payment.PaymentCreatedEvent;
import com.kodlamaio.rentalService.business.abstracts.RentalService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);
	private RentalService rentalService;

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "create")
	public void consume(PaymentCreatedEvent event) {
		LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
		rentalService.setConditionByPayment(event.getRentalId());
		// save the order event into the database
	}
}
