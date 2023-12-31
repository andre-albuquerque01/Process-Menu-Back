package com.ae.tech.ProcessMenu.entity.dto;

public record ProductResponseDTO(String title, String subTitle, String description, int qtd_itens, String observation, double price,
		int waitTime, boolean status, String file_name, String categorie, String position) {

}
