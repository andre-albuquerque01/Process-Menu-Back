package com.ae.tech.ProcessMenu.entity.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Order")
public class Order {
	@Id
	private String id;

	private OrderProducts products;

	private String numberOrder;

	private String idUser;

	private String formPay;

	private String qtdItens;

	private String table;

	private double precoTotal;

	private boolean status;

	private double impostoTributos;

	private String nfe;

	public Order() {
	}

	public Order(OrderProducts products, String numberOrder, String idUser, String formPay, String qtdItens,
			String table, double precoTotal, boolean status, double impostoTributos, String nfe) {
		this.products = products;
		this.numberOrder = numberOrder;
		this.idUser = idUser;
		this.formPay = formPay;
		this.qtdItens = qtdItens;
		this.table = table;
		this.precoTotal = precoTotal;
		this.status = status;
		this.impostoTributos = impostoTributos;
		this.nfe = nfe;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OrderProducts getProducts() {
		return products;
	}

	public void setProducts(OrderProducts products) {
		this.products = products;
	}

	public String getNumberOrder() {
		return numberOrder;
	}

	public void setNumberOrder(String numberOrder) {
		this.numberOrder = numberOrder;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getFormPay() {
		return formPay;
	}

	public void setFormPay(String formPay) {
		this.formPay = formPay;
	}

	public String getQtdItens() {
		return qtdItens;
	}

	public void setQtdItens(String qtdItens) {
		this.qtdItens = qtdItens;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public double getImpostoTributos() {
		return impostoTributos;
	}

	public void setImpostoTributos(double impostoTributos) {
		this.impostoTributos = impostoTributos;
	}

	public String getNfe() {
		return nfe;
	}

	public void setNfe(String nfe) {
		this.nfe = nfe;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", products=" + products + ", numberOrder=" + numberOrder + ", idUser=" + idUser
				+ ", formPay=" + formPay + ", qtdItens=" + qtdItens + ", table=" + table + ", precoTotal=" + precoTotal
				+ ", status=" + status + ", impostoTributos=" + impostoTributos + ", nfe=" + nfe + "]";
	}

}
