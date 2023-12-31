package com.ae.tech.ProcessMenu.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ae.tech.ProcessMenu.entity.dto.OrderResponseDTO;
import com.ae.tech.ProcessMenu.entity.product.Order;
import com.ae.tech.ProcessMenu.entity.users.User;
import com.ae.tech.ProcessMenu.repositorio.OrderRepository;
import com.ae.tech.ProcessMenu.repositorio.UserRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	public void registerOrder(OrderResponseDTO data) throws Exception {
		Optional<User> idUser = this.userRepository.findById(data.idUser());
		var numberOrder = RandomService.generateRandomAlphaNumeric(20);
		if (!orderRepository.findByNumberOrder(numberOrder).isEmpty() || !idUser.isPresent())
			throw new Exception("Erro na geração do número ou usuário não listado");

		Order _order = new Order(data.products(), numberOrder, data.idUser(), data.formPay(), data.qtdItens(),
				data.table(), data.precoTotal(), data.status(), data.impostoTributos(), data.nfe(), data.tip(),
				data.create_at(), data.update_at());
		Order saveorder = this.orderRepository.save(_order);
	}

	public Order updateOrder(String id, Order data) throws Exception {
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
			_Order.setUpdate_at(data.getUpdate_at());
			_Order.setTip(data.getTip());
			_Order = orderRepository.save(_Order);
			return _Order;
		} else {
			throw new Exception("Update não foi realizado");
		}
	}
}
