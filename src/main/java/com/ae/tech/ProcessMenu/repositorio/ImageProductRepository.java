package com.ae.tech.ProcessMenu.repositorio;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ae.tech.ProcessMenu.entity.product.ImageProduct;

public interface ImageProductRepository extends MongoRepository<ImageProduct, String>{

}
