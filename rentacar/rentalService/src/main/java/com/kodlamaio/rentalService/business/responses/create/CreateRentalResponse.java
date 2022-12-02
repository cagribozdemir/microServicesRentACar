package com.kodlamaio.rentalService.business.responses.create;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalResponse {
	private String id;
	private String carId;
	private LocalDateTime dateStarted;
	private int rentedForDays;
	private double dailyPrice;
	private double totalPrice; 
}
