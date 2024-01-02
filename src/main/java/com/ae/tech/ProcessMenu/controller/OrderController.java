package com.ae.tech.ProcessMenu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ae.tech.ProcessMenu.entity.dto.OrderResponseDTO;
import com.ae.tech.ProcessMenu.entity.product.Order;
import com.ae.tech.ProcessMenu.repositorio.OrderRepository;
import com.ae.tech.ProcessMenu.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderService orderService;

	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getAllOrders() {
		try {
			List<Order> list = orderRepository.findAll();
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") String id) {
		try {
			Optional<Order> orderOptional = orderRepository.findById(id);

			if (orderOptional.isPresent()) {
				Order order = orderOptional.get();
				return new ResponseEntity<>(order, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/userId/{id}")
	public ResponseEntity<List<Order>> getOrderByUserId(@PathVariable(value = "id") String id) {
		try {
			List<Order> orders = orderRepository.findAllByIdUser(id);
			if (!orders.isEmpty()) {
				return new ResponseEntity<>(orders, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchOrder/{number}")
	public ResponseEntity<Order> getBySearchOrder(@PathVariable(value = "number") String number) {
		try {
			Optional<Order> orders = orderRepository.findByNumberOrder(number);
			if (orders.isPresent()) {
				Order order = orders.get();
				return new ResponseEntity<>(order, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Order> registerOrder(@RequestBody @Valid OrderResponseDTO data) {
		try {
			orderService.registerOrder(data);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") String id, @Valid @RequestBody Order data) {
		try {
			orderService.updateOrder(id, data);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PatchMapping("/updateStatus/{id}")
	public ResponseEntity<Order> updateStatus(@PathVariable(value = "id") String id, @Valid @RequestBody OrderResponseDTO data) {
		System.out.println(data);
		System.out.println(id);
		try {
			Optional<Order> orderData = orderRepository.findById(id);
			if (orderData.isPresent()) {
				System.out.println("Entrou na etapa");
				Order _order = orderData.get();
				_order.setStatus(data.stats());
				_order.setUpdate_at(data.update_at());
				_order = orderRepository.save(_order);
				return new ResponseEntity<>(null, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@DeleteMapping("/del/{id}")
	public ResponseEntity<String> deleteOrder(@PathVariable(value = "id") String id) {
		try {
			orderRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
