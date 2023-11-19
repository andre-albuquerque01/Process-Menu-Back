package com.ae.tech.ProcessMenu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@PostMapping("/insert")
	public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderResponseDTO data) {
		try {
			Order _order = new Order(data.procuct(), data.idUser(), data.formPay(), data.qtdItens(), data.table(),
					data.precoTotal(), data.status(), data.impostoTributos(), data.nfe());
			Order saveorder = this.orderRepository.save(_order);
			return ResponseEntity.ok(saveorder);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
