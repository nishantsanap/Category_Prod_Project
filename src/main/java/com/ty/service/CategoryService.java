package com.ty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.entity.Category;

import com.ty.repository.CategoryRepository;
import com.ty.responsestructure.ResponseStructure;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

//	 public ResponseEntity<ResponseStructure<Category>> createCategory(Category category) {
//	        ResponseStructure<Category> rs = new ResponseStructure<>();
//
//	        // Validate the input
//	        if (category.getName() == null || category.getName().isEmpty()) {
//	            rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
//	            rs.setMessage("Category name cannot be null or empty");
//	            rs.setData(null);
//	            return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
//	        }
//
//	        // Save the category
//	        Category savedCategory = categoryRepository.save(category);
//
//	        // Prepare the response
//	        rs.setStatusCode(HttpStatus.CREATED.value());
//	        rs.setMessage("Category created successfully");
//	        rs.setData(savedCategory);
//
//	        return new ResponseEntity<>(rs, HttpStatus.CREATED);
//	    }

	public ResponseEntity<ResponseStructure<Category>> createCategory(String name) {
		// Create object to hold the response
		ResponseStructure<Category> rs = new ResponseStructure<>();

		// validate the user input
		if (name == null || name.isEmpty()) {
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setMessage("Category name cannot be null or empty");
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}

		// Create the category object
		Category category = new Category();
		category.setName(name);

	
		Category savedCategory = categoryRepository.save(category);

		//prepare the resp
		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Category created successfully");
		rs.setData(savedCategory);

		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

	public ResponseStructure<Page<Category>> getAllCategories(Pageable pageable) {
		Page<Category> categoryPage = categoryRepository.findAll(pageable);

		// Create and return the response structure
		ResponseStructure<Page<Category>> response = new ResponseStructure<>();
		response.setStatusCode(200);
		response.setMessage("Categories fetched successfully");
		response.setData(categoryPage);

		return response;
	}

	public ResponseEntity<ResponseStructure<Category>> getCategoryByCid(Integer cid) {
		ResponseStructure<Category> rs = new ResponseStructure<>();

		// Fetch the category by using cid
		Category category = categoryRepository.findById(cid).orElse(null);

		if (category == null) {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Category not found for CID: " + cid);
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
		}

	
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Category fetched successfully");
		rs.setData(category);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	//method to update operations by using cid
	public ResponseEntity<ResponseStructure<Category>> updateCategoryByCid(Integer cid, Category categoryDetails) {
		ResponseStructure<Category> rs = new ResponseStructure<>();

		// Check if category exists
		Category existingCategory = categoryRepository.findById(cid).orElse(null);

		if (existingCategory == null) {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Category not found for CID: " + cid);
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
		}

		// Update the existing category with new details
		existingCategory.setName(categoryDetails.getName());
		
	

		// Save the updated category
		Category updatedCategory = categoryRepository.save(existingCategory);

	
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Category updated successfully");
		rs.setData(updatedCategory);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

        //method to delete operations by using cid
	public ResponseEntity<ResponseStructure<String>> deleteCategoryByCid(Integer cid) {
		ResponseStructure<String> rs = new ResponseStructure<>();

	        // Check if category exists
		Category existingCategory = categoryRepository.findById(cid).orElse(null);

		if (existingCategory == null) {
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Category not found for CID: " + cid);
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
		}

		// delete category
		categoryRepository.deleteById(cid);

		
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Category deleted successfully");
		rs.setData("Category with CID " + cid + " has been deleted.");

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

}
