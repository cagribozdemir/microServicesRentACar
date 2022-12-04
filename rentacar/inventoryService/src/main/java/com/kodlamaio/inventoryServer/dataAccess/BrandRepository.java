package com.kodlamaio.inventoryServer.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.inventoryServer.entities.concretes.Brand;

public interface BrandRepository extends JpaRepository<Brand, String> {
	Brand findByName(String name);
}

