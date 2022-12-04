package com.kodlamaio.paymentService.business.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.PaymentCreatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.paymentService.business.abstracts.PaymentService;
import com.kodlamaio.paymentService.business.requests.CreatePaymentRequest;
import com.kodlamaio.paymentService.business.responses.CreatePaymentResponse;
import com.kodlamaio.paymentService.dataAccess.PaymentRepository;
import com.kodlamaio.paymentService.entities.Payment;
import com.kodlamaio.paymentService.kafka.PaymentProducer;
import com.kodlamaio.paymentService.webApi.RentalApi;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService{
	private PaymentRepository paymentRepository;
	private ModelMapperService modelMapperService;
	private PaymentProducer paymentProducer;
	private RentalApi rentalApi;

	@Override
	public CreatePaymentResponse add(CreatePaymentRequest createPaymentRequest) {
		checkBalanceEnough(createPaymentRequest.getBalance(),createPaymentRequest.getRentalId());
		
		Payment payment = modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setId(UUID.randomUUID().toString());
		
		Payment createdPayment = paymentRepository.save(payment);
		
		PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent();
		paymentCreatedEvent.setRentalId(createdPayment.getRentalId());
		paymentCreatedEvent.setMessage("Payment Created");
		
		this.paymentProducer.sendMessage(paymentCreatedEvent);
		
		CreatePaymentResponse createPaymentResponse = modelMapperService.forResponse().map(payment, CreatePaymentResponse.class);
		
		return createPaymentResponse;
	}
	
	private void checkBalanceEnough(double balance, String rentalId) {
		if (balance<rentalApi.getTotalPrice(rentalId)) {
			throw new BusinessException("BALANCE.IS.NOT.ENOUGH");
		}
	}
	
}
