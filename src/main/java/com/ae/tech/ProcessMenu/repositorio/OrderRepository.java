package com.ae.tech.ProcessMenu.repositorio;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ae.tech.ProcessMenu.entity.product.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	Optional<Order> findByNumberOrder(String number);
}
