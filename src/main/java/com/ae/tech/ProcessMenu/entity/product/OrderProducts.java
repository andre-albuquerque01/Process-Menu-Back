package com.ae.tech.ProcessMenu.entity.product;

public class OrderProducts {

	private String id;

	private String title;

	private String observation;

	private double price;

	private String waitTime;

	private String file_name;

	private int qtd_itens;

	public OrderProducts() {
	}

	public OrderProducts(String id, String title, String observation, double price, String waitTime, String file_name,
			int qtd_itens) {
		this.id = id;
		this.title = title;
		this.observation = observation;
		this.price = price;
		this.waitTime = waitTime;
		this.file_name = file_name;
		this.qtd_itens = qtd_itens;
	}

	@Override
	public String toString() {
		return "OrderProducts [id=" + id + ", title=" + title + ", observation=" + observation + ", price=" + price
				+ ", waitTime=" + waitTime + ", file_name=" + file_name + ", qtd_itens=" + qtd_itens + "]";
	}

	public int getQtd_itens() {
		return qtd_itens;
	}

	public void setQtd_itens(int qtd_itens) {
		this.qtd_itens = qtd_itens;
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

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

}
