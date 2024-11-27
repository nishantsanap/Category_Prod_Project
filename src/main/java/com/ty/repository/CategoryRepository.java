package com.ty.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ty.entity.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer>{ //*
	Optional<Category> findById(Integer cid);
}
