package com.kodlamaio.inventoryServer.business.abstracts;

import java.util.List;

import com.kodlamaio.inventoryServer.business.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryServer.business.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryServer.business.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryServer.business.responses.get.GetBrandResponse;
import com.kodlamaio.inventoryServer.business.responses.update.UpdateBrandResponse;

public interface BrandService {
	List<GetAllBrandsResponse> getAll();
	CreateBrandResponse add(CreateBrandRequest createBrandRequest);
	void delete(String id);
	UpdateBrandResponse update(UpdateBrandRequest updateBrandRequest);
	GetBrandResponse getById(String id); 
}
