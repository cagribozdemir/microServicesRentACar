package com.kodlamaio.invoiceService.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {
	private String id;

	private String paymentId;
	
	private String customerFirstName;

	private String customerLastName;

	private double tax;

	private double totalPrice;

	private String address;
}
