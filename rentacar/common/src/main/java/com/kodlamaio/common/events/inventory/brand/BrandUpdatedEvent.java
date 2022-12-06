package com.kodlamaio.common.events.inventory.brand;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandUpdatedEvent {
	private String message;
	private String brandId;
	private String brandName;
}
