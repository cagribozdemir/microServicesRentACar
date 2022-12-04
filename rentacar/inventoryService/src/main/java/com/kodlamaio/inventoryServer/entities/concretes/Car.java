package com.kodlamaio.inventoryServer.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "dailyPrice")
	private double dailyPrice;
	
	@Column(name = "modelYear")
	private int modelYear;
	
	@Column(name = "plate")
	private String plate;
	
	@Column(name = "state")
	private int state;
	
	@ManyToOne
	@JoinColumn(name = "model_id")
	private Model model;
	
	
}
