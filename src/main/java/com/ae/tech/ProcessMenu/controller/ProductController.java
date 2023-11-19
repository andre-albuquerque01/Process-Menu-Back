package com.ae.tech.ProcessMenu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ae.tech.ProcessMenu.entity.DTO.ProductResponseDTO;
import com.ae.tech.ProcessMenu.entity.product.Product;
import com.ae.tech.ProcessMenu.repositorio.ProductRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping("/")
	public ResponseEntity<List<Product>> getAllProduct() {
		try {
			List<Product> list = new ArrayList<Product>();
			if (!list.isEmpty()) {
				productRepository.findAll().forEach(list::add);
				return new ResponseEntity<>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductResponseDTO data) {
	    try {
	        Product _product = new Product(data.title(), data.description(), data.qtd_itens(), data.observation(),
	                data.preco(), data.tempo_espera(), data.status(), data.file_name(), data.categoria());
	        Product savedProduct = this.productRepository.save(_product);
	        return ResponseEntity.ok(savedProduct);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@PutMapping("/alt/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") String id,
			@Valid @RequestBody Product data) {
		try {
			Optional<Product> productData = productRepository.findById(id);
			Product _Product = productData.get();
			_Product.setTitle(data.getTitle());
			_Product.setDescription(data.getDescription());
			_Product.setQtd_itens(data.getQtd_itens());
			_Product.setObservation(data.getObservation());
			_Product.setPreco(data.getPreco());
			_Product.setTempo_espera(data.getTempo_espera());
			_Product.setStatus(data.isStatus());
			_Product.setFile_name(data.getFile_name());
			_Product.setCategoria(data.getCategoria());
			return new ResponseEntity<>(productRepository.save(_Product), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@DeleteMapping("/del/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable(value = "id") String id) {
		try {
			productRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
