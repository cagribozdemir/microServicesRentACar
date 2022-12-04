package com.kodlamaio.inventoryServer.dataAccess;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kodlamaio.inventoryServer.entities.dtos.CarDetailDto;

public interface CarDetailRepository extends MongoRepository<CarDetailDto, String>{

}
