package com.ae.tech.ProcessMenu.entity.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Product")
public class Product {

	@Id
	private String id;

	private String title;

	private String subTitle;

	private String description;

	private int qtd_itens;

	private String observation;

	private double price;

	private String waitTime;

	private boolean status;

	private String file_name;

	private String categorie;

	private String like;

//	Posição onde vai ficar no sistema
	private String position;

	public Product() {
	}

	public Product(String title, String subTitle, String description, int qtd_itens, String observation, double price,
			String waitTime, boolean status, String file_name, String categorie, String position) {
		this.title = title;
		this.subTitle = subTitle;
		this.description = description;
		this.qtd_itens = qtd_itens;
		this.observation = observation;
		this.price = price;
		this.waitTime = waitTime;
		this.status = status;
		this.file_name = file_name;
		this.categorie = categorie;
		this.position = position;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", subTitle=" + subTitle + ", description=" + description
				+ ", qtd_itens=" + qtd_itens + ", observation=" + observation + ", price=" + price + ", waitTime="
				+ waitTime + ", status=" + status + ", file_name=" + file_name + ", categorie=" + categorie + ", like="
				+ like + ", position=" + position + "]";
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

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
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

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
