package com.ae.tech.ProcessMenu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.ae.tech.ProcessMenu.entity.product.ImageProduct;
import com.ae.tech.ProcessMenu.entity.product.Product;
import com.ae.tech.ProcessMenu.repositorio.ImageProductRepository;
import com.ae.tech.ProcessMenu.repositorio.ProductRepository;
import com.ae.tech.ProcessMenu.services.ImageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ImageProductRepository imageProductRepository;

	@GetMapping("/")
	public ResponseEntity<List<Product>> getAllProduct() {
		try {
			List<Product> list = productRepository.findAll();
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchProduct/{name}")
	public ResponseEntity<List<Product>> getNameProduct(@PathVariable(value = "name") String name) {
		try {
			List<Product> listTitle = productRepository.findByTitle(name);
			if (listTitle.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(listTitle, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/searchCategory/{category}")
	public ResponseEntity<List<Product>> getCategory(@PathVariable(value = "category") String category) {
		try {
			List<Product> list = productRepository.findByCategorie(category);
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductResponseDTO data) throws IOException {
		try {
			String file = imageService.getFileName();
			String fileId = imageService.getFileId();
			if (file.isEmpty())
				return ResponseEntity.badRequest().build();
			Product _product = new Product(data.title(), data.description(), data.qtd_itens(), data.observation(),
					data.preco(), data.tempo_espera(), data.status(), file, fileId, data.categorie(), data.position());
			imageService.setFileName("");
			imageService.setFileId("");
			Product savedProduct = this.productRepository.save(_product);
			return ResponseEntity.ok(savedProduct);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/image")
	public ResponseEntity<ImageProduct> createImage(@RequestBody MultipartFile file) throws IOException {
		try {
			var saveImage = imageService.saveImageToStorage(file);
			ImageProduct _image = new ImageProduct(saveImage);
			ImageProduct savedImageProduct = this.imageProductRepository.save(_image);
			imageService.savePathName(savedImageProduct.getId(), savedImageProduct.getFileName());
			return ResponseEntity.ok(savedImageProduct);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/like/{id}")
	public ResponseEntity<Product> updateLike(@PathVariable(value = "id") String id, @Valid @RequestParam String like) {
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
	
	@PatchMapping("/qtd/{id}")
	public ResponseEntity<Product> updateQtd(@PathVariable(value = "id") String id, @Valid @RequestParam Integer qtd) {
		try {
			Optional<Product> productData = productRepository.findById(id);
			if (productData.isPresent()) {
				Product _Product = productData.get();
				_Product.setQtd_itens(qtd);
				return new ResponseEntity<>(productRepository.save(_Product), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}


	@PutMapping("/image/{id}")
	public ResponseEntity<ImageProduct> updateImage(@PathVariable(value = "id") String id,
			@RequestBody MultipartFile file) throws IOException {
		try {
			Optional<ImageProduct> imageProduct = imageProductRepository.findById(id);
			if (imageProduct.isPresent()) {
				var saveImage = imageService.saveImageToStorage(file);
				ImageProduct _image = imageProduct.get();
				_image.setFileName(saveImage);
				imageService.savePathName(id, saveImage);
				var save = imageProductRepository.save(_image);
				return new ResponseEntity<>(save, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/alt/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") String id,
			@Valid @RequestBody Product data) {
		try {
			String file = imageService.getFileName();
			String idImage = imageService.getFileId();
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().build();
			}
			Optional<Product> productData = productRepository.findById(id);
			if (productData.isPresent() && !file.isEmpty()) {
				Product _Product = productData.get();
				_Product.setTitle(data.getTitle());
				_Product.setDescription(data.getDescription());
				_Product.setQtd_itens(data.getQtd_itens());
				_Product.setObservation(data.getObservation());
				_Product.setPreco(data.getPreco());
				_Product.setTempo_espera(data.getTempo_espera());
				_Product.setStatus(data.isStatus());
				_Product.setFile_name(file);
				_Product.setIdImage(idImage);
				_Product.setCategorie(data.getCategorie());
				_Product.setPosition(data.getPosition());
				imageService.setFileName("");
				imageService.setFileId("");
				return ResponseEntity.ok().build();
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
