package com.kodlamaio.filterService.webApi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.filterService.business.abstracts.FilterService;
import com.kodlamaio.filterService.business.responses.get.GetAllFiltersResponse;
import com.kodlamaio.filterService.business.responses.get.GetFilterResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/filters/")
@AllArgsConstructor
public class FilterController {
	private FilterService filterService;
	
	@GetMapping
	public List<GetAllFiltersResponse> getAll(){
		return filterService.getAll();
	}
	
	@GetMapping("{brandName}")
	public List<GetAllFiltersResponse> getByBrandName(@PathVariable String brandName) {
		return filterService.getByBrandName(brandName);
	}
	
	@GetMapping("{modelName}")
	public List<GetAllFiltersResponse> getByModelName(@PathVariable String modelName) {
		return filterService.getByModelName(modelName);
	}
	
	@GetMapping("{plate}")
	public GetFilterResponse getByPlate(@PathVariable String plate) {
		return filterService.getByPlate(plate);
	}
	
	@GetMapping("dailyprice/{min}-{max}")
	public List<GetAllFiltersResponse> getByDailyPrice(@PathVariable double min,@PathVariable double max) {
		return filterService.getByDailyPrice(min, max);
	}
	
	@GetMapping("modelyear/{min}-{max}")
	public List<GetAllFiltersResponse> getByModelYear(@PathVariable int min,@PathVariable int max) {
		return filterService.getByModelYear(min, max);
	}
	
}
