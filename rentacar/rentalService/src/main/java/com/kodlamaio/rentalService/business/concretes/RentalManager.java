package com.kodlamaio.rentalService.business.concretes;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.RentalCreatedEvent;
import com.kodlamaio.common.events.RentalUpdatedEvent;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentalService.business.abstracts.RentalService;
import com.kodlamaio.rentalService.business.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalService.business.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalService.business.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalService.business.responses.update.UpdateRentalResponse;
import com.kodlamaio.rentalService.dataAccess.RentalRepository;
import com.kodlamaio.rentalService.entities.Rental;
import com.kodlamaio.rentalService.kafka.RentalProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;
	private RentalProducer rentalProducer;

	@Override
	public CreateRentalResponse add(CreateRentalRequest createRentalRequest) {
		Rental rental = this.modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		rental.setId(UUID.randomUUID().toString());
		rental.setDateStarted(LocalDateTime.now());
		double totalPrice = createRentalRequest.getDailyPrice() * createRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);
		
		Rental rentalCreated = this.rentalRepository.save(rental);

		RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
		rentalCreatedEvent.setCarId(rentalCreated.getCarId());
		rentalCreatedEvent.setMessage("Rental Created");

		this.rentalProducer.sendMessage(rentalCreatedEvent);

		CreateRentalResponse createRentalResponse = this.modelMapperService.forResponse().map(rental,
				CreateRentalResponse.class);
		return createRentalResponse;
	}

	@Override
	public UpdateRentalResponse update(UpdateRentalRequest updateRentalRequest) {
		Rental rental = this.rentalRepository.findByCarId(updateRentalRequest.getOldCarId());
		rental.setCarId(updateRentalRequest.getNewCarId());
		rental.setDailyPrice(updateRentalRequest.getDailyPrice());
		rental.setDateStarted(LocalDateTime.now());
		rental.setRentedForDays(updateRentalRequest.getRentedForDays());
		double totalPrice = updateRentalRequest.getDailyPrice() * updateRentalRequest.getRentedForDays();
		rental.setTotalPrice(totalPrice);

		Rental rentalUpdated = this.rentalRepository.save(rental);
		
		
		RentalUpdatedEvent rentalUpdatedEvent = new RentalUpdatedEvent();
		rentalUpdatedEvent.setOldCarId(updateRentalRequest.getOldCarId());
		rentalUpdatedEvent.setNewCarId(updateRentalRequest.getNewCarId());
		rentalUpdatedEvent.setMessage("Rental Updated");
		
		this.rentalProducer.sendMessage(rentalUpdatedEvent);
		
		UpdateRentalResponse updateRentalResponse = this.modelMapperService.forResponse().map(rentalUpdated, UpdateRentalResponse.class);
		updateRentalResponse.setCarId(rentalUpdated.getCarId());
		
		return updateRentalResponse;
	}

}
