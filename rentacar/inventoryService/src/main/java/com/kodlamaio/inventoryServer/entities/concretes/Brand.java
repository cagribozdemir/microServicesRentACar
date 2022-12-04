package com.kodlamaio.inventoryServer.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brands")
public class Brand {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;
	
	@OneToMany(mappedBy = "brand")
	private List<Model> models; 
	
}
