package com.kodlamaio.inventoryServer.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

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
import com.kodlamaio.inventoryServer.entities.Car;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
	private CarRepository carRepository;
	private ModelMapperService modelMapperService;
	private ModelService modelService;
	
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
		this.carRepository.save(car);
		
		CreateCarResponse createCarResponse = this.modelMapperService.forResponse().map(car, CreateCarResponse.class);
		return createCarResponse;
	}

	@Override
	public void delete(String id) {
		checkIfCarExistsById(id);
		carRepository.deleteById(id);
		
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateCarRequest) {
		checkIfModelExistsByModelId(updateCarRequest.getModelId());
		checkIfCarExistsById(updateCarRequest.getId());
		checkIfCarExistsByPlate(updateCarRequest.getPlate());
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		car.setState(1);

		this.carRepository.save(car);

		UpdateCarResponse updateCarResponse = this.modelMapperService.forResponse().map(car,
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
		System.out.println(car.getDailyPrice());
		car.setState(state);
		carRepository.save(car);
	}
	
	private void checkIfCarExistsById(String id) {
		Car result = carRepository.findById(id).orElse(null);
		if (result == null ) {
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

	@Override
	public void checkIfCarAvailable(String id) {
		Car result = carRepository.findById(id).orElse(null);
		if (result.getState() != 1 || result == null) {
			throw new BusinessException("CAR.NO.AVAILABLE");
		}
		
	}


}
