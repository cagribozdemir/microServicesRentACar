package com.kodlamaio.inventoryServer.business.abstracts;

import java.util.List;

import com.kodlamaio.inventoryServer.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryServer.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryServer.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetModelResponse;
import com.kodlamaio.inventoryServer.business.responses.update.UpdateModelResponse;

public interface ModelService {
	List<GetAllModelsResponse> getAll();
	CreateModelResponse add(CreateModelRequest createModelRequest);
	void delete(String id);
	UpdateModelResponse update(UpdateModelRequest updateModelRequest);
	GetModelResponse getById(String id);
}
