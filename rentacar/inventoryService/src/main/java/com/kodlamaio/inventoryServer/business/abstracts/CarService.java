package com.kodlamaio.inventoryServer.business.abstracts;

import java.util.List;

import com.kodlamaio.inventoryServer.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryServer.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryServer.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetCarResponse;
import com.kodlamaio.inventoryServer.business.responses.update.UpdateCarResponse;

public interface CarService {
	List<GetAllCarsResponse> getAll();
	CreateCarResponse add(CreateCarRequest createCarRequest);
	void delete(String id);
	UpdateCarResponse update(UpdateCarRequest updateCarRequest);
	GetCarResponse getById(String id);
	void updateCarState(String id, int state);
	void checkIfCarAvailable(String id);
}
