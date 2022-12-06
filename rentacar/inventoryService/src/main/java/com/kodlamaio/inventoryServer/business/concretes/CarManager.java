package com.kodlamaio.inventoryServer.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.inventory.car.CarCreatedEvent;
import com.kodlamaio.common.events.inventory.car.CarDeletedEvent;
import com.kodlamaio.common.events.inventory.car.CarUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryServer.business.abstracts.CarService;
import com.kodlamaio.inventoryServer.business.abstracts.ModelService;
import com.kodlamaio.inventoryServer.business.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryServer.business.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryServer.business.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetCarResponse;
import com.kodlamaio.inventoryServer.business.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryServer.dataAccess.CarRepository;
import com.kodlamaio.inventoryServer.entities.concretes.Car;
import com.kodlamaio.inventoryServer.kafka.InventoryProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private ModelService modelService;
	private InventoryProducer inventoryProducer;

	@Override
	public List<GetAllCarsResponse> getAll() {
		List<Car> cars = this.carRepository.findAll();

		List<GetAllCarsResponse> response = cars.stream()
				.map(car -> this.modelMapperService.forResponse().map(car, GetAllCarsResponse.class))
				.collect(Collectors.toList());

		return response;
	}

	@Override
	public CreateCarResponse add(CreateCarRequest createCarRequest) {
		checkIfModelExistsByModelId(createCarRequest.getModelId());
		checkIfCarExistsByPlate(createCarRequest.getPlate());

		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		car.setId(UUID.randomUUID().toString());
		car.setState(1);
		Car createdCar = this.carRepository.save(car);

		CarCreatedEvent carCreatedEvent = this.modelMapperService.forRequest().map(createdCar,
				CarCreatedEvent.class);
		carCreatedEvent.setMessage("Car Created");
		carCreatedEvent.setCarId(createdCar.getId());
		carCreatedEvent.setModelId(createdCar.getModel().getId());
		carCreatedEvent.setModelName(createdCar.getModel().getName());
		carCreatedEvent.setBrandId(createdCar.getModel().getBrand().getId());
		carCreatedEvent.setBrandName(createdCar.getModel().getBrand().getName());
		
		this.inventoryProducer.sendMessage(carCreatedEvent);
		
		CreateCarResponse createCarResponse = this.modelMapperService.forResponse().map(createdCar,
				CreateCarResponse.class);
		return createCarResponse;
	}

	@Override
	public void delete(String id) {
		checkIfCarExistsById(id);
		carRepository.deleteById(id);
		CarDeletedEvent carDeletedEvent = new CarDeletedEvent();
		carDeletedEvent.setCarId(id);
		carDeletedEvent.setMessage("Car Deleted");
		
		this.inventoryProducer.sendMessage(carDeletedEvent);
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateCarRequest) {
		checkIfModelExistsByModelId(updateCarRequest.getModelId());
		checkIfCarExistsById(updateCarRequest.getId());
		checkIfCarExistsByPlate(updateCarRequest.getPlate());

		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		car.setState(1);

		Car updatedCar = this.carRepository.save(car);
		
		CarUpdatedEvent carUpdatedEvent = this.modelMapperService.forRequest().map(updatedCar, CarUpdatedEvent.class);
		carUpdatedEvent.setMessage("Car Created");
		carUpdatedEvent.setCarId(updatedCar.getId());
		carUpdatedEvent.setModelId(updatedCar.getModel().getId());
		carUpdatedEvent.setModelName(updatedCar.getModel().getName());
		carUpdatedEvent.setBrandId(updatedCar.getModel().getBrand().getId());
		carUpdatedEvent.setBrandName(updatedCar.getModel().getBrand().getName());
		
		this.inventoryProducer.sendMessage(carUpdatedEvent);

		UpdateCarResponse updateCarResponse = this.modelMapperService.forResponse().map(updatedCar,
				UpdateCarResponse.class);

		return updateCarResponse;
	}

	@Override
	public GetCarResponse getById(String id) {
		checkIfCarExistsById(id);
		Car car = carRepository.findById(id).get();
		GetCarResponse getCarResponse = this.modelMapperService.forResponse().map(car, GetCarResponse.class);
		return getCarResponse;
	}

	@Override
	public void updateCarState(String id, int state) {
		Car car = carRepository.findById(id).get();
		car.setState(state);
		carRepository.save(car);
	}

	@Override
	public void checkIfCarAvailable(String id) {
		Car result = carRepository.findById(id).orElse(null);
		if (result.getState() != 1 || result == null) {
			throw new BusinessException("CAR.NO.AVAILABLE");
		}

	}

	private void checkIfCarExistsById(String id) {
		Car result = carRepository.findById(id).orElse(null);
		if (result == null) {
			throw new BusinessException("CAR.NO.EXISTS");
		}
	}

	private void checkIfCarExistsByPlate(String plate) {
		var result = carRepository.findByPlate(plate);
		if (result != null) {
			throw new BusinessException("CAR.EXISTS");
		}
	}

	private void checkIfModelExistsByModelId(String modelId) {
		var result = modelService.getById(modelId);
		if (result == null) {
			throw new BusinessException("MODEL.NO.EXİSTS");
		}
	}
}
