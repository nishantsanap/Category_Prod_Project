package com.ty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.entity.Category;
import com.ty.entity.Products;
import com.ty.repository.CategoryRepository;
import com.ty.repository.ProductRepository;
import com.ty.responsestructure.ResponseStructure;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	public ResponseEntity<ResponseStructure<Products>> createProduct(Products product) {
		ResponseStructure<Products> rs = new ResponseStructure<>();

		// Validate the input
		if (product.getName() == null || product.getName().isEmpty()) {
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setMessage("Product name cannot be null or empty");
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}

		if (product.getPrice() == null || product.getPrice() <= 0) {
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setMessage("Product price must be greater than zero");
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}

		if (product.getQuantity() == null || product.getQuantity() < 0) {
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setMessage("Product quantity cannot be negative");
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}

		// Check if the category exists
		Category category = categoryRepository.findById(product.getCategory().getCid()).orElse(null);
		if (category == null) {
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setMessage("Category not found for the provided CID");
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}

		// Set the category to the product
		product.setCategory(category);

		// Save the product
		Products savedProduct = productRepository.save(product);

		// Prepare the response
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Product created successfully");
		rs.setData(savedProduct);

		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Products>> getProductById(Integer pid) {
		ResponseStructure<Products> rs = new ResponseStructure<>();

		// Find the product by pid
		Products product = productRepository.findById(pid).orElse(null);

		if (product == null) {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Product not found with ID: " + pid);
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
		}

		// Prepare the response
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Product fetched successfully");
		rs.setData(product);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Page<Products>>> getAllProducts(Pageable pageable) {
		ResponseStructure<Page<Products>> rs = new ResponseStructure<>();

		// Get paginated products from the repository
		Page<Products> productPage = productRepository.findAll(pageable);

		if (productPage.isEmpty()) {
			rs.setStatusCode(HttpStatus.NO_CONTENT.value());
			rs.setMessage("No products found");
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.NO_CONTENT);
		}

		// Prepare the response structure with paginated products
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Products fetched successfully");
		rs.setData(productPage);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Products>> updateProduct(Integer pid, Products updatedProduct) {
		ResponseStructure<Products> response = new ResponseStructure<>();

		// Find the existing product by its pid
		Products existingProduct = productRepository.findById(pid).orElse(null);

		if (existingProduct == null) {
			// If product does not exist, return an error response
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage("Product not found");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		
		Category category = categoryRepository.findById(updatedProduct.getCategory().getCid()).orElse(null);

		if (category == null) {
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setMessage("Category not found for the provided CID");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// Update the existing product fields with the new data
		existingProduct.setName(updatedProduct.getName());
		existingProduct.setDescription(updatedProduct.getDescription());
		existingProduct.setPrice(updatedProduct.getPrice());
		existingProduct.setQuantity(updatedProduct.getQuantity());
		existingProduct.setCategory(updatedProduct.getCategory()); 
		existingProduct.setCategory(category);
		
		Products savedProduct = productRepository.save(existingProduct);

		
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Product updated successfully");
		response.setData(savedProduct);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<String>> deleteProduct(Integer pid) {
		ResponseStructure<String> response = new ResponseStructure<>();

		
		Products product = productRepository.findById(pid).orElse(null);

		if (product == null) {
			
			response.setStatusCode(HttpStatus.NOT_FOUND.value());
			response.setMessage("Product not found");
			response.setData(null);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		// Delete the product
		productRepository.delete(product);

		// Prepare the success response
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Product deleted successfully");
		response.setData("Product with pid " + pid + " has been deleted");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
