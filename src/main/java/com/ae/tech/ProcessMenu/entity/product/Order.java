package com.ae.tech.ProcessMenu.entity.product;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Order")
public class Order {
	@Id
	private String id;

	private List<OrderProducts> products;

	private String numberOrder;

	private String idUser;

	private String formPay;

	private String qtdItens;

	private String table;

	private double precoTotal;

	private String status;

	private double impostoTributos;

	private String nfe;

	private double tip;

	private String create_at;

	private String update_at;

	public Order() {
	}

	public Order(List<OrderProducts> products, String numberOrder, String idUser, String formPay, String qtdItens,
			String table, double precoTotal, String status, double impostoTributos, String nfe, double tip,
			String create_at, String update_at) {
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
		this.create_at = create_at;
		this.update_at = update_at;
		this.tip = tip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<OrderProducts> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProducts> products) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

	public double getTip() {
		return tip;
	}

	public void setTip(double tip) {
		this.tip = tip;
	}

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", products=" + products + ", numberOrder=" + numberOrder + ", idUser=" + idUser
				+ ", formPay=" + formPay + ", qtdItens=" + qtdItens + ", table=" + table + ", precoTotal=" + precoTotal
				+ ", status=" + status + ", impostoTributos=" + impostoTributos + ", nfe=" + nfe + ", tip=" + tip
				+ ", create_at=" + create_at + ", update_at=" + update_at + "]";
	}

}
