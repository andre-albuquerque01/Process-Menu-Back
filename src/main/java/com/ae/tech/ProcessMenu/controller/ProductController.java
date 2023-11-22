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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ae.tech.ProcessMenu.entity.DTO.ProductResponseDTO;
import com.ae.tech.ProcessMenu.entity.product.Product;
import com.ae.tech.ProcessMenu.repositorio.ProductRepository;
import com.ae.tech.ProcessMenu.services.ImageService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ImageService imageService;

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

	@GetMapping("/searchProduct/{name}")
	public ResponseEntity<List<Product>> getNameProduct(@PathVariable(value = "name") String name) {
		List<Product> listTitle = productRepository.findByTitle(name);
		if (!listTitle.isEmpty()) {
			return new ResponseEntity<>(listTitle, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/searchCategory/{category}")
	public ResponseEntity<List<Product>> getCategory(@PathVariable(value = "category") String category) {
		List<Product> listCategory = productRepository.findByCategoria(category);
		if (!listCategory.isEmpty()) {
			return new ResponseEntity<>(listCategory, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Product> createProduct(@RequestParam("file_name") MultipartFile file_name, @RequestBody @Valid ProductResponseDTO data) {
		try {
			var saveImg = imageService.saveImageToStorage("Documentos/img/", file_name);
			Product _product = new Product(data.title(), data.description(), data.qtd_itens(), data.observation(),
					data.preco(), data.tempo_espera(), data.status(), saveImg, data.categoria());
			Product savedProduct = this.productRepository.save(_product);
			return ResponseEntity.ok(savedProduct);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/like/{id}")
	public ResponseEntity<Product> createLike(@PathVariable(value = "id") String id, @Valid @RequestParam String like) {
		try {
			Optional<Product> productData = productRepository.findById(id);
			if (productData.isPresent()) {
				Product _Product = productData.get();
				_Product.setLike(like);
				return new ResponseEntity<>(productRepository.save(_Product), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PutMapping("/alt/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") String id,
			@Valid @RequestBody Product data) {
		try {
			Optional<Product> productData = productRepository.findById(id);
			if (productData.isPresent()) {
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
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
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
