package com.kodlamaio.paymentService.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.payment.PaymentCreatedEvent;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentProducer.class);

	private NewTopic topic;
	
	private KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplateCreated;

	
	public void sendMessage(PaymentCreatedEvent paymentCreatedEvent) {
		LOGGER.info(String.format("Payment created event => %s", paymentCreatedEvent.toString()));
		
		Message<PaymentCreatedEvent> message = MessageBuilder
				.withPayload(paymentCreatedEvent)
				.setHeader(KafkaHeaders.TOPIC, topic.name()).build();		
		kafkaTemplateCreated.send(message);
	}
}
