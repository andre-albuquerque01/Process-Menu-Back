package com.ae.tech.ProcessMenu.controller;

import java.security.SecureRandom;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ae.tech.ProcessMenu.entity.DTO.OrderResponseDTO;
import com.ae.tech.ProcessMenu.entity.product.Order;
import com.ae.tech.ProcessMenu.repositorio.OrderRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final SecureRandom RANDOM = new SecureRandom();

	public static String generateRandomAlphaNumeric(int length) {
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int randomIndex = RANDOM.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			sb.append(randomChar);
		}

		return sb.toString();
	}

	@GetMapping("/")
	public ResponseEntity<List<Order>> getAllOrder() {
		try {
			List<Order> list = new ArrayList<Order>();
			if (!list.isEmpty()) {
				orderRepository.findAll().forEach(list::add);
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchOrder/{number}")
	public ResponseEntity<List<Order>> getBySearchOrder(@PathVariable(value = "number") String number) {
		List<Order> orders = orderRepository.findByNumberOrder(number);
		if (!orders.isEmpty()) {
			return new ResponseEntity<>(orders, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/insert")
	public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderResponseDTO data) {
		try {
			String numberOrder = generateRandomAlphaNumeric(10);
			if(orderRepository.findByNumberOrder(numberOrder) != null)
				return ResponseEntity.badRequest().build();
			
			Order _order = new Order(data.product(), numberOrder, data.idUser(), data.formPay(), data.qtdItens(),
					data.table(), data.precoTotal(), data.status(), data.impostoTributos(), data.nfe());
			Order saveorder = this.orderRepository.save(_order);
			return ResponseEntity.ok(saveorder);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/alt/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") String id, @Valid @RequestBody Order data) {
		Optional<Order> orderData = orderRepository.findById(id);
		try {
			Order _Order = orderData.get();
			_Order.setProducts(data.getProducts());
			_Order.setIdUser(data.getIdUser());
			_Order.setFormPay(data.getFormPay());
			_Order.setQtdItens(data.getQtdItens());
			_Order.setTable(data.getTable());
			_Order.setPrecoTotal(data.getPrecoTotal());
			_Order.setStatus(data.isStatus());
			_Order.setImpostoTributos(data.getImpostoTributos());
			_Order.setNfe(data.getNfe());
			return new ResponseEntity<>(orderRepository.save(_Order), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@DeleteMapping("/del/{id}")
	public ResponseEntity<HttpStatus> deleteOrder(@PathVariable(value = "id") String id) {
		try {
			orderRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
