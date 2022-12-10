package com.kodlamaio.rentalService.business.concretes;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.rental.RentalCreatedEvent;
import com.kodlamaio.common.events.rental.RentalUpdatedEvent;
import com.kodlamaio.common.requests.CreatePaymentRequest;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentalService.business.abstracts.RentalService;
import com.kodlamaio.rentalService.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalService.business.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalService.business.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalService.business.responses.update.UpdateRentalResponse;
import com.kodlamaio.rentalService.dataAccess.RentalRepository;
import com.kodlamaio.rentalService.entities.Rental;
import com.kodlamaio.rentalService.kafka.RentalProducer;
import com.kodlamaio.rentalService.webApi.controller.CarApi;
import com.kodlamaio.rentalService.webApi.controller.PaymentApi;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private CarApi carApi;
	private PaymentApi paymentApi;
	private RentalProducer rentalProducer;
 
	@Override
	public CreateRentalResponse add(CreateRentalRequest createRentalRequest, CreatePaymentRequest createPaymentRequest) {
		carApi.checkIfCarAvailable(createRentalRequest.getCarId());
		
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		rental.setDateStarted(LocalDateTime.now());
		double totalPrice = createRentalRequest.getDailyPrice() * createRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);
		checkBalanceEnough(createPaymentRequest.getBalance(),totalPrice);
		
		paymentApi.add(createPaymentRequest);
		
		Rental rentalCreated = this.rentalRepository.save(rental);
		
		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(rentalCreated.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");

		this.rentalProducer.sendMessage(rentalCreatedEvent);
		
		CreateRentalResponse createRentalResponse = this.modelMapperService.forResponse().map(rentalCreated,
				CreateRentalResponse.class);
		return createRentalResponse;
	}

	@Override
	public UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest) {
		checkIfRentalById(updateRentalRequest.getId());
		carApi.checkIfCarAvailable(updateRentalRequest.getCarId());
		RentalUpdatedEvent rentalUpdatedEvent = new RentalUpdatedEvent();
		
		Rental rental = this.rentalRepository.findById(updateRentalRequest.getId()).get();
		rentalUpdatedEvent.setOldCarId(rental.getCarId());
		
		//this.modelMapperService.forRequest().map(updateRentalRequest, Rental.class);
		rental.setCarId(updateRentalRequest.getCarId());
		rental.setDailyPrice(updateRentalRequest.getDailyPrice());
		rental.setRentedForDays(updateRentalRequest.getRentedForDays());
		rental.setDateStarted(LocalDateTime.now());
		double totalPrice = updateRentalRequest.getDailyPrice() * updateRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);

		Rental rentalUpdated = this.rentalRepository.save(rental);
		
		
		UpdateRentalResponse updateRentalResponse = this.modelMapperService.forResponse().map(rentalUpdated, UpdateRentalResponse.class);
		
		return updateRentalResponse;
	}
	
	@Override
	public double getTotalPrice(String id) {
		return rentalRepository.findById(id).get().getTotalPrice();
	}
	
	@Override
	public void setConditionByPayment(String id) {
		Rental rental = this.rentalRepository.findById(id).get();
		if (rental.getCondition()==1) {
			rental.setCondition(2);	
		}
		rentalRepository.save(rental);
		
	}
	
	private void checkIfRentalById(String id) {
		var result = rentalRepository.findById(id);
		if (result == null) {
			throw new BusinessException("RENTAL.NO.EXISTS");
		}
	}
	
	private void checkBalanceEnough(double balance, double totalPrice) {
		if (balance<totalPrice) {
			throw new BusinessException("BALANCE.IS.NOT.ENOUGH");
		}
	}
}
