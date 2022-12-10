package com.kodlamaio.paymentService.business.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.payment.PaymentCreatedEvent;
import com.kodlamaio.common.requests.CreatePaymentRequest;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.paymentService.business.abstracts.PaymentService;
import com.kodlamaio.paymentService.business.adapters.PosCheckService;
import com.kodlamaio.paymentService.dataAccess.PaymentRepository;
import com.kodlamaio.paymentService.entities.Payment;
import com.kodlamaio.paymentService.kafka.PaymentProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService{
	private PaymentRepository paymentRepository;
	private ModelMapperService modelMapperService;
	private PosCheckService posCheckService;
	private PaymentProducer paymentProducer;

	@Override
	public void add(CreatePaymentRequest createPaymentRequest) {
		Payment payment = modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setId(UUID.randomUUID().toString());
		posCheckService.pay();
		
		paymentRepository.save(payment);
		
		PaymentCreatedEvent paymentCreatedEvent = new PaymentCreatedEvent();
		paymentCreatedEvent.setMessage("Payment Created");
		
		this.paymentProducer.sendMessage(paymentCreatedEvent);
	}
	
	@Override
	public void updateStatus(String id, int status) {
		Payment payment = this.paymentRepository.findById(id).get();
		payment.setStatus(status);
		paymentRepository.save(payment);
		
	}
	
	@Override
	public void delete(String id) {
		paymentRepository.deleteById(id);
		
	}
}
