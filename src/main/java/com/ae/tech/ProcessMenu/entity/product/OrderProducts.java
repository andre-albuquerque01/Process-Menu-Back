package com.ae.tech.ProcessMenu.entity.product;

public class OrderProducts {

	private String id;

	private String title;

	private String observation;

	private double preco;

	private String tempo_espera;

	private String file_name;

	public OrderProducts() {
	}

	public OrderProducts(String id, String title, String observation, double preco, String tempo_espera, String file_name) {
		this.id = id;
		this.title = title;
		this.observation = observation;
		this.preco = preco;
		this.tempo_espera = tempo_espera;
		this.file_name = file_name;
	}

	@Override
	public String toString() {
		return "OrderProducts [id=" + id + ", title=" + title + ", observation=" + observation + ", preco=" + preco
				+ ", tempo_espera=" + tempo_espera + ", file_name=" + file_name + "]";
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

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

}
