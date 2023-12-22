package com.ae.tech.ProcessMenu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ae.tech.ProcessMenu.entity.DTO.OrderResponseDTO;
import com.ae.tech.ProcessMenu.entity.product.Order;
import com.ae.tech.ProcessMenu.entity.product.Product;
import com.ae.tech.ProcessMenu.repositorio.OrderRepository;
import com.ae.tech.ProcessMenu.repositorio.ProductRepository;
import com.ae.tech.ProcessMenu.services.RandomService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderRepository orderRepository;

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
	public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderResponseDTO data) {
		try {
			var numberOrder = RandomService.generateRandomAlphaNumeric(20);
			if (orderRepository.findByNumberOrder(numberOrder) == null)
				return ResponseEntity.badRequest().build();

			Order _order = new Order(data.products(), numberOrder, data.idUser(), data.formPay(), data.qtdItens(),
					data.table(), data.precoTotal(), data.status(), data.impostoTributos(), data.nfe(),
					data.dateOrder(), data.tip());
			Order saveorder = this.orderRepository.save(_order);
			return ResponseEntity.ok(saveorder);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") String id, @Valid @RequestBody Order data) {
		try {
			Optional<Order> orderData = orderRepository.findById(id);
			Order _Order = orderData.get();
			if (orderData.isPresent()) {
				_Order.setProducts(data.getProducts());
				_Order.setFormPay(data.getFormPay());
				_Order.setQtdItens(data.getQtdItens());
				_Order.setTable(data.getTable());
				_Order.setPrecoTotal(data.getPrecoTotal());
				_Order.setStatus(data.getStatus());
				_Order.setImpostoTributos(data.getImpostoTributos());
				_Order.setNfe(data.getNfe());
				_Order.setDateOrder(data.getDateOrder());
				_Order.setTip(data.getTip());
				return new ResponseEntity<>(orderRepository.save(_Order), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PatchMapping("/updateStatus/{id}")
	public ResponseEntity<Order> updateStatus(@PathVariable(value = "id") String id, @Valid @RequestBody Order data) {
		Optional<Order> orderData = orderRepository.findById(id);
		try {
			Order _order = orderData.get();
			_order.setStatus(data.getStatus());
			return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
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
