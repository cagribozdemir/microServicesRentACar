package com.kodlamaio.common.events.Invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceCreatedEvent {
	private String message;
	private String paymentId;
}
