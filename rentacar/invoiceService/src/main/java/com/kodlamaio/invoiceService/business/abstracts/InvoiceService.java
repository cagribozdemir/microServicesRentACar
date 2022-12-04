package com.kodlamaio.invoiceService.business.abstracts;

import java.util.List;

import com.kodlamaio.invoiceService.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoiceService.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.invoiceService.business.responses.CreateInvoiceResponse;
import com.kodlamaio.invoiceService.business.responses.GetAllInvoicesResponse;
import com.kodlamaio.invoiceService.business.responses.GetInvoiceResponse;
import com.kodlamaio.invoiceService.business.responses.UpdateInvoiceResponse;

public interface InvoiceService {
	List<GetAllInvoicesResponse> getAll();
	CreateInvoiceResponse add(CreateInvoiceRequest createInvoiceRequest);
	void delete(String id);
	UpdateInvoiceResponse update(UpdateInvoiceRequest updateInvoiceRequest);
	GetInvoiceResponse getById(String id);
}
