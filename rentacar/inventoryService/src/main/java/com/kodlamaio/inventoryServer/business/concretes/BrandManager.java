package com.kodlamaio.inventoryServer.business.concretes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.common.events.inventory.brand.BrandUpdatedEvent;
import com.kodlamaio.common.utilities.exceptions.BusinessException;
import com.kodlamaio.common.utilities.mapping.ModelMapperService;
import com.kodlamaio.inventoryServer.business.abstracts.BrandService;
import com.kodlamaio.inventoryServer.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryServer.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryServer.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetBrandResponse;
import com.kodlamaio.inventoryServer.business.responses.update.UpdateBrandResponse;
import com.kodlamaio.inventoryServer.dataAccess.BrandRepository;
import com.kodlamaio.inventoryServer.entities.concretes.Brand;
import com.kodlamaio.inventoryServer.kafka.InventoryProducer;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
	private BrandRepository brandRepository;
	private ModelMapperService modelMapperService;
	private InventoryProducer inventoryProducer;

	@Override
	public List<GetAllBrandsResponse> getAll() {
		List<Brand> brands = this.brandRepository.findAll();

		List<GetAllBrandsResponse> response = brands.stream()
				.map(brand -> this.modelMapperService.forResponse().map(brand, GetAllBrandsResponse.class))
				.collect(Collectors.toList());

		return response;
	}

	@Override
	public CreateBrandResponse add(CreateBrandRequest createBrandRequest) {
		checkIfBrandExistsByName(createBrandRequest.getName());
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		brand.setId(UUID.randomUUID().toString());

		this.brandRepository.save(brand);

		CreateBrandResponse createBrandResponse = this.modelMapperService.forResponse().map(brand,
				CreateBrandResponse.class);
		return createBrandResponse;
	}

	@Override
	public void delete(String id) {
		checkIfBrandExistsById(id);
		brandRepository.deleteById(id);
	}

	@Override
	public UpdateBrandResponse update(UpdateBrandRequest updateBrandRequest) {
		checkIfBrandExistsById(updateBrandRequest.getId());
		checkIfBrandExistsByName(updateBrandRequest.getName());
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		
		Brand updatedBrand = this.brandRepository.save(brand);
		
		BrandUpdatedEvent brandUpdatedEvent = new BrandUpdatedEvent();
		brandUpdatedEvent.setBrandId(updatedBrand.getId());
		brandUpdatedEvent.setBrandName(updatedBrand.getName());
		brandUpdatedEvent.setMessage("Brand Updated");

		this.inventoryProducer.sendMessage(brandUpdatedEvent);
		
		UpdateBrandResponse updateBrandResponse = this.modelMapperService.forResponse().map(brand,
				UpdateBrandResponse.class);
		return updateBrandResponse;
	}

	@Override
	public GetBrandResponse getById(String id) {
		checkIfBrandExistsById(id);
		Brand brand = brandRepository.findById(id).get();
		GetBrandResponse getBrandResponse = this.modelMapperService.forResponse().map(brand, GetBrandResponse.class);
		return getBrandResponse;
	}

	private void checkIfBrandExistsByName(String name) {
		Brand currentBrand = this.brandRepository.findByName(name);
		if (currentBrand != null) {
			throw new BusinessException("BRAND.EXISTS");
		}
	}
	
	private void checkIfBrandExistsById(String id) {
		var result = this.brandRepository.findById(id);
		if (result == null) {
			throw new BusinessException("BRAND.NO.EXISTS");
		}
	}
}
