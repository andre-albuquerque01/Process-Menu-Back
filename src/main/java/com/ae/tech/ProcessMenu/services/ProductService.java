package com.ae.tech.ProcessMenu.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ae.tech.ProcessMenu.entity.dto.ProductResponseDTO;
import com.ae.tech.ProcessMenu.entity.product.Product;
import com.ae.tech.ProcessMenu.repositorio.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	public Product registerProduct(ProductResponseDTO data) throws Exception {
		if (data == null)
			throw new Exception("Email existente");

		Product _product = new Product(data.title(), data.subTitle(), data.description(), data.qtd_itens(),
				data.observation(), data.price(), data.waitTime(), true, data.file_name(), data.categorie(),
				data.position());
		Product savedProduct = this.productRepository.save(_product);
		return savedProduct;
	}

	public Product updateProduct(String id, Product data) throws Exception {
		Optional<Product> productData = productRepository.findById(id);
		if (productData.isEmpty()) {
			throw new Exception("Produto não localizado");
		}

		Product _Product = productData.get();
		if (productData.isPresent()) {
			_Product.setTitle(data.getTitle());
			_Product.setSubTitle(data.getSubTitle());
			_Product.setDescription(data.getDescription());
			_Product.setQtd_itens(data.getQtd_itens());
			_Product.setObservation(data.getObservation());
			_Product.setPrice(data.getPrice());
			_Product.setWaitTime(data.getWaitTime());
			_Product.setStatus(data.isStatus());
			_Product.setFile_name(data.getFile_name());
			_Product.setCategorie(data.getCategorie());
			_Product.setPosition(data.getPosition());
			_Product = productRepository.save(_Product);
			return _Product;
		} else {
			throw new Exception("Update não foi realizado");
		}
	}

	public Product updateLike(String id, String like) throws Exception {
		Optional<Product> productData = productRepository.findById(id);
		if (productData.isEmpty()) {
			throw new Exception("Produto não localizado");
		}
		Product _Product = productData.get();
		_Product.setLike(like);
		_Product = productRepository.save(_Product);
		return _Product;
	}
	
	public Product updateStatus(String id) throws Exception {
		Optional<Product> productData = productRepository.findById(id);
		if (productData.isEmpty()) {
			throw new Exception("Produto não localizado");
		}
		Product _Product = productData.get();
		_Product.setStatus(false);
		_Product = productRepository.save(_Product);
		return _Product;
	}
	
	public Product updateQtd(String id, Integer qtd) throws Exception {
		Optional<Product> productData = productRepository.findById(id);
		if (productData.isEmpty()) {
			throw new Exception("Produto não localizado");
		}
		Product _Product = productData.get();
		_Product.setQtd_itens(qtd);
		_Product = productRepository.save(_Product);
		return _Product;
	}
}
