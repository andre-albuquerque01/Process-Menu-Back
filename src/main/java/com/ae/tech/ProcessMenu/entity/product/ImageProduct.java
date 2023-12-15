package com.ae.tech.ProcessMenu.entity.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ImageProduct")
public class ImageProduct {
	@Id
	private String id;

	private String fileName;

	public String getId() {
		return id;
	}

	public ImageProduct() {
	}

	public ImageProduct( String fileName) {
		this.fileName = fileName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
