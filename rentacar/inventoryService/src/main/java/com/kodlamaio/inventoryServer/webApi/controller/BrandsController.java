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

import com.kodlamaio.inventoryServer.business.abstracts.BrandService;
import com.kodlamaio.inventoryServer.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryServer.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryServer.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetBrandResponse;
import com.kodlamaio.inventoryServer.business.responses.update.UpdateBrandResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/brands")
@AllArgsConstructor
public class BrandsController {
	private BrandService brandService;

	@GetMapping
	public List<GetAllBrandsResponse> getAll() {
		return brandService.getAll();
	}

	@GetMapping("/{id}")
	public GetBrandResponse getById(@PathVariable String id) {
		return brandService.getById(id);
	}

	@PostMapping
	public CreateBrandResponse add(@Valid @RequestBody CreateBrandRequest createBrandRequest) {
		return brandService.add(createBrandRequest);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		brandService.delete(id);
	}

	@PutMapping
	public UpdateBrandResponse update(@Valid @RequestBody UpdateBrandRequest updateBrandRequest) {
		return brandService.update(updateBrandRequest);
	}
}
