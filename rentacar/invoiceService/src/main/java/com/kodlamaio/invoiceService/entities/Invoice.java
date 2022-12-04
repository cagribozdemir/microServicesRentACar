package com.kodlamaio.invoiceService.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "paymentId")
	private String paymentId;
	
	@Column(name = "customerFirstName")
	private String customerFirstName;
	
	@Column(name = "customerLastName")
	private String customerLastName;

	@Column(name = "tax")
	private double tax;
	
	@Column(name = "totalPrice")
	private double totalPrice;
	
	@Column(name = "address")
	private String address;
}
