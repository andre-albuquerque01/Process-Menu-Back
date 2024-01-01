package com.ae.tech.ProcessMenu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.ae.tech.ProcessMenu.entity.dto.ProductResponseDTO;
import com.ae.tech.ProcessMenu.entity.product.ImageProduct;
import com.ae.tech.ProcessMenu.entity.product.Product;
import com.ae.tech.ProcessMenu.repositorio.ImageProductRepository;
import com.ae.tech.ProcessMenu.repositorio.ProductRepository;
import com.ae.tech.ProcessMenu.services.ImageService;
import com.ae.tech.ProcessMenu.services.ProductService;

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
	
	@Autowired
	private ProductService productService;

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

	@GetMapping("/searchProduct/{title}")
	public ResponseEntity<List<Product>> getNameProduct(@PathVariable(value = "title") String title) {
		try {
			List<Product> listTitle = productRepository.findByTitle(title);
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

	@GetMapping("/findProduct/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable(value = "id") String id) {
		try {
			Optional<Product> list = productRepository.findById(id);
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(list.get(), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Product> registerProduct(@RequestBody @Valid ProductResponseDTO data) throws IOException {
		try {
			productService.registerProduct(data);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/register/image")
	public ResponseEntity<ImageProduct> createImage(@RequestBody MultipartFile file) throws IOException {
		try {
			if (file == null)
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			System.out.println(file);
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

	@PatchMapping("/update/qtd/{id}")
	public ResponseEntity<Product> updateQtd(@PathVariable(value = "id") String id, @Valid @RequestParam Integer qtd) {
		try {
			productService.updateQtd(id, qtd);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PutMapping("/update/image/{id}")
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

	@PutMapping("/update/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") String id,
			@Valid @RequestBody Product data) {
		try {
			productService.updateProduct(id, data);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PatchMapping("/like/{id}")
	public ResponseEntity<Product> updateLike(@PathVariable(value = "id") String id, @Valid @RequestParam String like) {
		try {
			productService.updateLike(id, like);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PatchMapping("/updateStatus/{id}")
	public ResponseEntity<Product> updateStatus(@PathVariable(value = "id") String id) {
		try {
			productService.updateStatus(id);
			return new ResponseEntity<>(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
