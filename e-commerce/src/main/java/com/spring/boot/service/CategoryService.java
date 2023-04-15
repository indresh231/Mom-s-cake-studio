package com.spring.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.model.Category;
import com.spring.boot.repository.CategoryRepository;

@Service
public class CategoryService {

	
	@Autowired
	CategoryRepository categoryRepository;
	
	
	//adding category
	public void addCategory(Category category) {
		categoryRepository.save(category);
	}
	
	//getting categories
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}
	//delete category
	public void removeCategoryById(int id ) {
		categoryRepository.deleteById(id);
	}
	
	//updating category
	
	public Optional<Category> getCategoryById(int id ){
		return categoryRepository.findById(id);
	}
}
