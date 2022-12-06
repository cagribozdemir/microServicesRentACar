package com.kodlamaio.invoiceService.business.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.Invoice.InvoiceCreatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.invoiceService.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceService.business.requests.CreateInvoiceRequest;
import com.kodlamaio.invoiceService.business.requests.UpdateInvoiceRequest;
import com.kodlamaio.invoiceService.business.responses.CreateInvoiceResponse;
import com.kodlamaio.invoiceService.business.responses.GetAllInvoicesResponse;
import com.kodlamaio.invoiceService.business.responses.GetInvoiceResponse;
import com.kodlamaio.invoiceService.business.responses.UpdateInvoiceResponse;
import com.kodlamaio.invoiceService.dataAccess.InvoiceRepository;
import com.kodlamaio.invoiceService.entities.Invoice;
import com.kodlamaio.invoiceService.kafka.InvoiceProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
	private InvoiceRepository invoiceRepository;
	private ModelMapperService modelMapperService;
	private InvoiceProducer invoiceProducer;
	
	@Override
	public List<GetAllInvoicesResponse> getAll() {
		return null;
	}

	@Override
	public CreateInvoiceResponse add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice = modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setId(UUID.randomUUID().toString());
		
		Invoice createdInvoice = invoiceRepository.save(invoice);
		
		InvoiceCreatedEvent invoiceCreatedEvent = new InvoiceCreatedEvent();
		invoiceCreatedEvent.setPaymentId(createdInvoice.getPaymentId());
		invoiceCreatedEvent.setMessage("Payment Created");
		
		this.invoiceProducer.sendMessage(invoiceCreatedEvent);
		
		CreateInvoiceResponse createPaymentResponse = modelMapperService.forResponse().map(invoice, CreateInvoiceResponse.class);
		
		return createPaymentResponse;
	}

	@Override
	public void delete(String id) {
		
	}

	@Override
	public UpdateInvoiceResponse update(UpdateInvoiceRequest updateInvoiceRequest) {
		return null;
	}

	@Override
	public GetInvoiceResponse getById(String id) {
		return null;
	}

}
