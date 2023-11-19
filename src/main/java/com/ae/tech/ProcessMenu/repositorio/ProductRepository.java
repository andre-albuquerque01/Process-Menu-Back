package com.ae.tech.ProcessMenu.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ae.tech.ProcessMenu.entity.product.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	ProductRepository findByCategoria(String categoria);
}
