package com.kodlamaio.common.events.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreatedEvent {
	private String message;
	private String rentalId;
}
