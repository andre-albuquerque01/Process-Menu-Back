package com.ae.tech.ProcessMenu.entity.DTO;

public record ProductResponseDTO(String title, String description, int qtd_itens,
		String observation, double preco, String tempo_espera, 
		boolean status, String file_name, String idImage ,String categorie, String typeProduct, String position) {

}
