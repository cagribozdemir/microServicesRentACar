package com.kodlamaio.inventoryServer.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.inventoryServer.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, String> {
	Brand findByName(String name);
}

