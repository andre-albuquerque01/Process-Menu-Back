package com.ae.tech.ProcessMenu.entity.DTO;

import java.util.List;

import com.ae.tech.ProcessMenu.entity.product.OrderProducts;

public record OrderResponseDTO(List<OrderProducts> products, String numberOrder, String idUser, String formPay, String qtdItens, String table, double precoTotal,
		String status, double impostoTributos, String nfe, String dateOrder, double tip) {

}
