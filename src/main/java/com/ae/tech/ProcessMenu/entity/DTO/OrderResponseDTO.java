package com.ae.tech.ProcessMenu.entity.DTO;

import java.util.List;

import com.ae.tech.ProcessMenu.entity.product.Product;

public record OrderResponseDTO(List<Product> procuct, String idUser, String formPay, String qtdItens, String table, double precoTotal,
		boolean status, double impostoTributos, String nfe) {

}
