package com.kodlamaio.inventoryServer.webApi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.inventoryServer.business.abstracts.CarService;
import com.kodlamaio.inventoryServer.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryServer.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryServer.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetCarResponse;
import com.kodlamaio.inventoryServer.business.responses.update.UpdateCarResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/cars")
@AllArgsConstructor
public class CarsController {
	private CarService carService;

	@GetMapping
	public List<GetAllCarsResponse> getAll() {
		return carService.getAll();
	}

	@GetMapping("/{id}")
	public GetCarResponse getById(@PathVariable String id) {
		return carService.getById(id);
	}

	@PostMapping
	public CreateCarResponse add(@Valid @RequestBody CreateCarRequest createCarRequest) {
		return carService.add(createCarRequest);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		carService.delete(id);
	}

	@PutMapping
	public UpdateCarResponse update(@Valid @RequestBody UpdateCarRequest updateCarRequest) {
		return carService.update(updateCarRequest);
	}
	
	@GetMapping("/checkcaravailable/{id}")
	public void checkIfCarAvailable(@PathVariable String id) {
		carService.checkIfCarAvailable(id);
	}
	
}
