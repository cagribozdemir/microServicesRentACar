package com.kodlamaio.inventoryServer.entities.dtos;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@Table(name = "cars")
@AllArgsConstructor
@NoArgsConstructor
public class CarDetailDto {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "brandId")
	private String brandId;

	@Column(name = "brandName")
	private String brandName;

	@Column(name = "modelId")
	private String modelId;

	@Column(name = "modelName")
	private String modelName;

	@Column(name = "dailyPrice")
	private double dailyPrice;

	@Column(name = "modelYear")
	private int modelYear;

	@Column(name = "plate")
	private String plate;

	@Column(name = "state")
	private int state;
}
