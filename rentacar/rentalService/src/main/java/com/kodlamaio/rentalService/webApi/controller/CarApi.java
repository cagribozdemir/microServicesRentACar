package com.kodlamaio.rentalService.webApi.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import feign.Headers;

@FeignClient(value = "carApi", url = "http://localhost:9010/stock/api/cars/")
public interface CarApi {
	@RequestMapping(method= RequestMethod.GET ,value = "checkcaravailable/{carId}")
	@Headers(value="Content-Type: application/json")
	void checkIfCarAvailable(@PathVariable String carId);
}
