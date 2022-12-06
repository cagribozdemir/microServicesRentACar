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

import com.kodlamaio.inventoryServer.business.abstracts.ModelService;
import com.kodlamaio.inventoryServer.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryServer.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryServer.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetModelResponse;
import com.kodlamaio.inventoryServer.business.responses.update.UpdateModelResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/models")
@AllArgsConstructor
public class ModelsController {
	private ModelService modelService;

	@GetMapping
	public List<GetAllModelsResponse> getAll() {
		return modelService.getAll();
	}

	@GetMapping("/{id}")
	public GetModelResponse getById(@PathVariable String id) {
		return modelService.getById(id);
	}

	@PostMapping
	public CreateModelResponse add(@Valid @RequestBody CreateModelRequest createModelRequest) {
		return modelService.add(createModelRequest);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		modelService.delete(id);
	}

	@PutMapping
	public UpdateModelResponse update(@Valid @RequestBody UpdateModelRequest updateModelRequest) {
		return modelService.update(updateModelRequest);
	}
}
