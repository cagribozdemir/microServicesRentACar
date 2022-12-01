package com.kodlamaio.inventoryServer.business.responses.get;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllCarsResponse {
	private String id;
	private double dailyPrice;
	private int modelYear;
	private String plate;
	private String state;
	private String brandName;
	private String modelName;
}
