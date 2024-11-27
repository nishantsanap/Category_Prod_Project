package com.ty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.ty.entity.Category;
import com.ty.responsestructure.ResponseStructure;
import com.ty.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

//	@PostMapping("/create")
//	public ResponseEntity<?> createCategory(@RequestBody Category category) {
//		return categoryService.createCategory(category);
//	}

	@PostMapping("/create")
	public ResponseEntity<?> createCategory(@RequestParam String name) {
		return categoryService.createCategory(name);
	}

	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<Page<Category>>> getAllCategories(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		ResponseStructure<Page<Category>> response = categoryService.getAllCategories(pageable);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/categories/{cid}")
	public ResponseEntity<ResponseStructure<Category>> getCategoryByCid(@PathVariable Integer cid) {
		return categoryService.getCategoryByCid(cid);
	}

	@PutMapping("/categories/{cid}")
	public ResponseEntity<ResponseStructure<Category>> updateCategoryByCid(@PathVariable Integer cid,
			@RequestBody Category categoryDetails) {
		return categoryService.updateCategoryByCid(cid, categoryDetails);
	}

	@DeleteMapping("/categories/{cid}")
	public ResponseEntity<ResponseStructure<String>> deleteCategoryByCid(@PathVariable Integer cid) {
		return categoryService.deleteCategoryByCid(cid);
	}

}
