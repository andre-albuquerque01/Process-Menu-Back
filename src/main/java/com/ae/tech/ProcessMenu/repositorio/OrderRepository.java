package com.ae.tech.ProcessMenu.repositorio;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ae.tech.ProcessMenu.entity.product.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
	List<Order> findByNumberOrder(String number);
}
