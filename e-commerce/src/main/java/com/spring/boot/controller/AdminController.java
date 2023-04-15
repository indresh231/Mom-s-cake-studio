package com.spring.boot.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.dto.ProductDTO;
import com.spring.boot.model.Category;
import com.spring.boot.model.Product;
import com.spring.boot.service.CategoryService;
import com.spring.boot.service.ProductService;

@Controller
public class AdminController {
	
	public static String uploadDir= "src/main/resources/static/productImages"; 
	@Autowired
	CategoryService categoryService;

	@GetMapping("/admin")
	public String adminHome() {
		
		
		return "adminHome";
	}
	

	@GetMapping("/admin/categories")
	public String getCat(Model model) {
		model.addAttribute("categories", categoryService.getAllCategory());
		
		return "categories";
	}

	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model model) {
		model.addAttribute("category", new Category());
		
		return "addCategories";
	}
	
	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category ) {
	
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	//delete category
	@GetMapping("/admin/categories/delete/{id}")
	
	public String deleteCat(@PathVariable int id ) {
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	//updating the category
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id , Model model) {
		Optional<Category> category=categoryService.getCategoryById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "addCategories";
		}else
		
			return "error404";
		
	}
	
	//product Section 
	@Autowired
	ProductService productService;
	//handler for products
	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products",productService.getAllProduct());
		return "products";
	}
	
//handler for product add
	@GetMapping("/admin/products/add")
	public String productAddGet(Model model) {
		model.addAttribute("productDTO",new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String productAddPost(@ModelAttribute("productDTO") ProductDTO productDTO,
			@RequestParam("productImage") MultipartFile file,
			@RequestParam("imgName")String  imgName ) throws IOException{
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		
		String imageUUID ;
		if(!file.isEmpty()) {
			imageUUID=file.getOriginalFilename();
			Path fileNameAndPath= Paths.get(uploadDir, imageUUID);
			Files.write(fileNameAndPath, file.getBytes());
		}else
		{
			imageUUID=imgName;
		}
			product.setImageName(imageUUID);
			productService.addProduct(product);
		
		
		
		return"redirect:/admin/products";
	}
	
	//delete product
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id) {
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	//product update handler
	@GetMapping("/admin/product/update/{id}")
	public String updateProduct(@PathVariable long id ,Model model) {
		
		Product product = productService.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", productDTO);
		
		return "productsAdd";
	}
}
