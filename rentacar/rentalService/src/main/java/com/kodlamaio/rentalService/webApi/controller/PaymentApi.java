package com.kodlamaio.rentalService.webApi.controller;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kodlamaio.common.requests.CreatePaymentRequest;

import feign.Headers;

@FeignClient(value = "paymentApi", url = "http://localhost:9010/payment/api/payments/")
public interface PaymentApi {
	@RequestMapping(method= RequestMethod.POST)
	@Headers(value="Content-Type: application/json")
	public void add(@Valid @RequestBody CreatePaymentRequest createPaymentRequest);
}
