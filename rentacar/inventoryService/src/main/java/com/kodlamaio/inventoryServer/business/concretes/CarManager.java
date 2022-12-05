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
import com.kodlamaio.inventoryServer.dataAccess.CarDetailRepository;
import com.kodlamaio.inventoryServer.dataAccess.CarRepository;
import com.kodlamaio.inventoryServer.entities.concretes.Car;
import com.kodlamaio.inventoryServer.entities.dtos.CarDetailDto;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
	private CarRepository carRepository;
	private CarDetailRepository carDetailRepository;
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
		Car createdCar = this.carRepository.save(car);

		CreateCarResponse createCarResponse = this.modelMapperService.forResponse().map(createdCar,
				CreateCarResponse.class);

		CarDetailDto carDetailDto = this.modelMapperService.forRequest().map(createdCar, CarDetailDto.class);
		carDetailDto.setBrandName(createdCar.getModel().getBrand().getName());
		carDetailDto.setBrandId(createdCar.getModel().getBrand().getId());
		this.carDetailRepository.save(carDetailDto);

		return createCarResponse;
	}

	@Override
	public void delete(String id) {
		checkIfCarExistsById(id);
		carRepository.deleteById(id);
		carDetailRepository.deleteById(id);
	}

	@Override
	public UpdateCarResponse update(UpdateCarRequest updateCarRequest) {
		checkIfModelExistsByModelId(updateCarRequest.getModelId());
		checkIfCarExistsById(updateCarRequest.getId());
		checkIfCarExistsByPlate(updateCarRequest.getPlate());

		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		car.setState(1);

		Car updatedCar = this.carRepository.save(car);

		UpdateCarResponse updateCarResponse = this.modelMapperService.forResponse().map(updatedCar,
				UpdateCarResponse.class);

		CarDetailDto carDetailDto = this.modelMapperService.forRequest().map(updatedCar, CarDetailDto.class);
		carDetailDto.setBrandName(updatedCar.getModel().getBrand().getName());
		carDetailDto.setBrandId(updatedCar.getModel().getBrand().getId());
		this.carDetailRepository.save(carDetailDto);

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
		Car updatedCar = carRepository.save(car);

		CarDetailDto carDetailDto = this.modelMapperService.forRequest().map(updatedCar, CarDetailDto.class);
		this.carDetailRepository.save(carDetailDto);

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
