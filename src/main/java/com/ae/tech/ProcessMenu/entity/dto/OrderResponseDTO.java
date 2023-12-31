package com.ae.tech.ProcessMenu.entity.dto;

import java.util.List;

import com.ae.tech.ProcessMenu.entity.product.OrderProducts;

public record OrderResponseDTO(List<OrderProducts> products, String numberOrder, String idUser, String formPay,
		String qtdItens, String table, double precoTotal, String status, double impostoTributos, String nfe, double tip,
		String create_at, String update_at) {

}
