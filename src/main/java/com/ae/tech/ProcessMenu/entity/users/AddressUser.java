package com.ae.tech.ProcessMenu.entity.users;

public class AddressUser {

	private String cep;

	private String logradouro;

	private String bairro;

	private String uf;

	private String complemento;

	public AddressUser() {

	}

	public AddressUser(String cep, String logradouro, String bairro, String uf, String complemento) {
		this.cep = cep;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.uf = uf;
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Override
	public String toString() {
		return "AddressUser [ 	cep=" + cep + ", logradouro=" + logradouro + ", bairro=" + bairro + ", uf=" + uf
				+ ", complemento=" + complemento + "]";
	}

}
