package com.ty.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ty.entity.Products;

public interface ProductRepository extends JpaRepository<Products, Integer>{
	Optional<Products> findById(Integer pid);
}
