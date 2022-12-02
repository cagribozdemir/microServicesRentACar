package com.kodlamaio.rentalService.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="rentals")
public class Rental {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "carId")
	private String carId;
	
	@Column(name = "dateStarted")
	private LocalDateTime dateStarted;

	@Column(name = "rentedForDays")
	private int rentedForDays;
	
	@Column(name = "dailyPrice")
	private double dailyPrice;
	
	@Column(name = "totalPrice")
	private double totalPrice;
	
	@Column(name = "condition")
	private int condition;
	
}
