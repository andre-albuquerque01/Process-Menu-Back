package com.ae.tech.ProcessMenu.entity.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Product")
public class Product {

	@Id
	private String id;

	private String title;

	private String description;

	private int qtd_itens;

	private String observation;

	private double preco;

	private String tempo_espera;

	private boolean status;

	private String file_name;

	private String categorie;

	private String like;

	private String typeProduct;
//	Posição onde vai ficar no sistema
	private String position;

	public Product() {
	}

	public Product(String title, String description, int qtd_itens, String observation, double preco,
			String tempo_espera, boolean status, String file_name, String categorie, String typeProduct,
			String position) {
		this.title = title;
		this.description = description;
		this.qtd_itens = qtd_itens;
		this.observation = observation;
		this.preco = preco;
		this.tempo_espera = tempo_espera;
		this.status = status;
		this.file_name = file_name;
		this.categorie = categorie;
		this.typeProduct = typeProduct;
		this.position = position;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", description=" + description + ", qtd_itens=" + qtd_itens
				+ ", observation=" + observation + ", preco=" + preco + ", tempo_espera=" + tempo_espera + ", status="
				+ status + ", file_name=" + file_name + ", categorie=" + categorie + ", like=" + like + ", typeProduct="
				+ typeProduct + ", position=" + position + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQtd_itens() {
		return qtd_itens;
	}

	public void setQtd_itens(int qtd_itens) {
		this.qtd_itens = qtd_itens;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getTempo_espera() {
		return tempo_espera;
	}

	public void setTempo_espera(String tempo_espera) {
		this.tempo_espera = tempo_espera;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getTypeProduct() {
		return typeProduct;
	}

	public void setTypeProduct(String typeProduct) {
		this.typeProduct = typeProduct;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
