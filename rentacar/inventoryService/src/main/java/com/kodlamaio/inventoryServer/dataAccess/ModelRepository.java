package com.kodlamaio.inventoryServer.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kodlamaio.inventoryServer.entities.concretes.Model;

public interface ModelRepository extends JpaRepository<Model, String> {
	Model findByName(String name);
}
