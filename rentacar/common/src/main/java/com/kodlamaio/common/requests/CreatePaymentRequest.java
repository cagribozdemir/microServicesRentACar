package com.kodlamaio.common.requests;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {	
	private String cardNo;
	
	private String cardHolder;

	private int cvv;
	
	private LocalDate cardDate;

	private double balance;
}
