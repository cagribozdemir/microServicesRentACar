package com.kodlamaio.rentalService.business.abstracts;

import com.kodlamaio.common.requests.CreatePaymentRequest;
import com.kodlamaio.rentalService.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalService.business.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalService.business.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalService.business.responses.update.UpdateRentalResponse;

public interface RentalService {
	CreateRentalResponse add(CreateRentalRequest createRentalRequest, CreatePaymentRequest createPaymentRequest);
	UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest);
	double getTotalPrice(String id);
	void setConditionByPayment(String id);
}
