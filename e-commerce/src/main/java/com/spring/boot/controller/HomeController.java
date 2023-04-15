package com.spring.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.spring.boot.global.GlobalData;
import com.spring.boot.service.CategoryService;
import com.spring.boot.service.ProductService;

@Controller
public class HomeController {
	
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	
	@GetMapping({"/","/home"})
	
	public String home(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		return "index";
	}
	
@GetMapping("/shop")
	
	public String showShop(Model model) {
	model.addAttribute("categories", categoryService.getAllCategory());
	model.addAttribute("products", productService.getAllProduct());
	model.addAttribute("cartCount", GlobalData.cart.size());
		return "shop";
	}

//handler for category wise shorting of product
@GetMapping("/shop/category/{id}")

public String shopByCategory(Model model, @PathVariable int id) {
model.addAttribute("categories", categoryService.getAllCategory());
model.addAttribute("products", productService.getAllProductByCategoryId(id));
	
	return "shop";
}
//view product when clicking on the image
@GetMapping("/shop/viewproduct/{id}")
public String viewProduct(Model model, @PathVariable int id) {
	model.addAttribute("product", productService.getProductById(id).get());
	model.addAttribute("cartCount", GlobalData.cart.size());
	return "viewProduct";
}
}
