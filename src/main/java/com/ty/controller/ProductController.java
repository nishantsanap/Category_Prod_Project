package com.ty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ty.entity.Products;
import com.ty.responsestructure.ResponseStructure;

import com.ty.service.ProductService;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;
         // create a new product operations
	@PostMapping("/products")
	public ResponseEntity<ResponseStructure<Products>> createProduct(@RequestBody Products product) {
		return productService.createProduct(product);
	}
	
        // Getting product by passing Id operations
	@GetMapping("/products/{pid}")
	public ResponseEntity<ResponseStructure<Products>> getProductById(
			@PathVariable Integer pid) {
		return productService.getProductById(pid); //// Call service to fetch product by pid

	}
       // Getting all products
	@GetMapping("/products")
	public ResponseEntity<ResponseStructure<Page<Products>>> getAllProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) // Default to 10 items per page
	{ 

		Pageable pageable = PageRequest.of(page, size); 
		return productService.getAllProducts(pageable); 
	}
	
        //update products by id operations
	@PutMapping("/products/{pid}")
	public ResponseEntity<ResponseStructure<Products>> updateProduct(@PathVariable Integer pid, 
																								
			@RequestBody Products product //// Get the updated product details from the request body

	) {
		return productService.updateProduct(pid, product);  // Call the service method to update the product

	}

	// Delete product by id operations
	@DeleteMapping("/products/{pid}")
	public ResponseEntity<ResponseStructure<String>> deleteProduct(@PathVariable Integer pid) {
		return productService.deleteProduct(pid); // Call the service method to delete the product
	}

}
