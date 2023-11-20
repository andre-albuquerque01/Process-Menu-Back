package com.ae.tech.ProcessMenu.entity.DTO;

import com.ae.tech.ProcessMenu.entity.product.OrderProducts;

public record OrderResponseDTO(OrderProducts product,  String idUser, String formPay, String qtdItens, String table, double precoTotal,
		boolean status, double impostoTributos, String nfe) {

}
