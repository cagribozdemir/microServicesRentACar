package com.kodlamaio.paymentService.business.abstracts;

import com.kodlamaio.paymentService.business.requests.CreatePaymentRequest;
import com.kodlamaio.paymentService.business.responses.CreatePaymentResponse;

public interface PaymentService {
	CreatePaymentResponse add(CreatePaymentRequest createPaymentRequest);
}
