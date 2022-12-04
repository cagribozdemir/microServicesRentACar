package com.kodlamaio.inventoryServer.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.inventoryServer.entities.concretes.Car;

public interface CarRepository extends JpaRepository<Car, String>{
	Car findByPlate(String plate);
}
