package com.spring.boot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.model.Product;
import com.spring.boot.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
ProductRepository productrepository;

	public List<Product>getAllProduct(){
		return productrepository.findAll();
	}
	public void addProduct(Product product) {
		productrepository.save(product);
	}
	public void removeProductById(long id ) {
		productrepository.deleteById(id);
	}
	
	public Optional<Product>getProductById(long id){
		return productrepository.findById(id);
	}
	
	public List<Product>getAllProductByCategoryId(int id){
		
		return productrepository.findAllByCategory_Id(id);
	}
}
