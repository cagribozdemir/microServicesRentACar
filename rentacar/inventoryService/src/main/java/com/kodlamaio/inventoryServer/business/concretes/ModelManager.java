package com.kodlamaio.inventoryServer.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.inventory.model.ModelUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryServer.business.abstracts.BrandService;
import com.kodlamaio.inventoryServer.business.abstracts.ModelService;
import com.kodlamaio.inventoryServer.business.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryServer.business.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryServer.business.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetModelResponse;
import com.kodlamaio.inventoryServer.business.responses.update.UpdateModelResponse;
import com.kodlamaio.inventoryServer.dataAccess.ModelRepository;
import com.kodlamaio.inventoryServer.entities.concretes.Model;
import com.kodlamaio.inventoryServer.kafka.InventoryProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {
	private ModelRepository modelRepository;
	private ModelMapperService modelMapperService;
	private BrandService brandService;
	private InventoryProducer inventoryProducer;

	@Override
	public List<GetAllModelsResponse> getAll() {
		List<Model> models = this.modelRepository.findAll();

		List<GetAllModelsResponse> response = models.stream()
				.map(model -> this.modelMapperService.forResponse().map(model, GetAllModelsResponse.class))
				.collect(Collectors.toList());

		return response;
	}

	@Override
	public CreateModelResponse add(CreateModelRequest createModelRequest) {
		checkIfModelExistsByName(createModelRequest.getName());
		checkIfBrandExistsByBrandId(createModelRequest.getBrandId());
		Model model = this.modelMapperService.forRequest().map(createModelRequest, Model.class);
		model.setId(UUID.randomUUID().toString());

		Model createdModel = this.modelRepository.save(model);

		CreateModelResponse createModelResponse = this.modelMapperService.forResponse().map(createdModel,
				CreateModelResponse.class);
		return createModelResponse;
	}

	@Override
	public void delete(String id) {
		checkIfModelExistsById(id);
		modelRepository.deleteById(id);
	}

	@Override
	public UpdateModelResponse update(UpdateModelRequest updateModelRequest) {
		checkIfModelExistsById(updateModelRequest.getId());
		checkIfModelExistsByName(updateModelRequest.getName());
		checkIfBrandExistsByBrandId(updateModelRequest.getBrandId());
		Model model = this.modelMapperService.forRequest().map(updateModelRequest, Model.class);

		Model updatedModel = this.modelRepository.save(model);
		
		ModelUpdatedEvent modelUpdatedEvent= new ModelUpdatedEvent();
		modelUpdatedEvent.setBrandId(updatedModel.getBrand().getId());
		modelUpdatedEvent.setBrandName(updatedModel.getBrand().getName());
		modelUpdatedEvent.setModelId(updatedModel.getId());
		modelUpdatedEvent.setModelName(updatedModel.getName());
		modelUpdatedEvent.setMessage("Model Updated");
		
		this.inventoryProducer.sendMessage(modelUpdatedEvent);

		UpdateModelResponse updateModelResponse = this.modelMapperService.forResponse().map(model,
				UpdateModelResponse.class);
		return updateModelResponse;
	}

	@Override
	public GetModelResponse getById(String id) {
		checkIfModelExistsById(id);
		Model model = modelRepository.findById(id).get();
		GetModelResponse getModelResponse = this.modelMapperService.forResponse().map(model, GetModelResponse.class);
		return getModelResponse;
	}

	private void checkIfModelExistsByName(String name) {
		Model currentModel = this.modelRepository.findByName(name);
		if (currentModel != null) {
			throw new BusinessException("MODEL.EXISTS");
		}
	}
	
	private void checkIfModelExistsById(String id) {
		var result = this.modelRepository.findById(id).orElse(null);
		if (result == null) {
			throw new BusinessException("MODEL.NO.EXISTS");
		}
	}
	
	private void checkIfBrandExistsByBrandId(String brandId) {
		var result = this.brandService.getById(brandId);
		if (result == null) {
			throw new BusinessException("BRAND.NO.EXISTS");
		}
	}
}
