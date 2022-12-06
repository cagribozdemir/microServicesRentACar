package com.kodlamaio.paymentService.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.Invoice.InvoiceCreatedEvent;
import com.kodlamaio.paymentService.business.abstracts.PaymentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceConsumer {
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceConsumer.class);
	private PaymentService paymentService;

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "create")
	public void consume(InvoiceCreatedEvent event) {
		LOGGER.info(String.format("Order event received in stock service => %s", event.toString()));
		paymentService.updateStatus(event.getPaymentId(), 2);
		// save the order event into the database
	}
}
