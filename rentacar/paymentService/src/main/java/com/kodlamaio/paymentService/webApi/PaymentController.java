package com.kodlamaio.paymentService.webApi;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.common.requests.CreatePaymentRequest;
import com.kodlamaio.paymentService.business.abstracts.PaymentService;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {
	private PaymentService paymentService;
	
	@PostMapping
	public void add(@Valid @RequestBody CreatePaymentRequest createPaymentRequest) {
		paymentService.add(createPaymentRequest);
	}
	
}
