package com.kodlamaio.rentalService.business.requests.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {
	private String oldCarId;
	private String newCarId;
	private int rentedForDays;
	private double dailyPrice;
}
