package com.kodlamaio.invoiceService.webApi.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.kodlamaio.invoiceService.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceService.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoiceService.business.responses.CreateInvoiceResponse;


public class InvoicesController {
private InvoiceService invoiceService;
	
	@PostMapping
	public CreateInvoiceResponse add(@Valid @RequestBody CreateInvoiceRequest createInvoiceRequest) {
		return invoiceService.add(createInvoiceRequest);
	}
}
