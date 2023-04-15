package com.spring.boot.controller;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.boot.global.GlobalData;
import com.spring.boot.model.Role;
import com.spring.boot.model.User;
import com.spring.boot.repository.RoleRepository;
import com.spring.boot.repository.UserRepository;

@Controller
public class LoginController {

	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}
	
	@GetMapping("/register")
	public String registerGet() {
		return "register";
	}
	@PostMapping("/register")
	public String registerPost( @ModelAttribute("user") User user, HttpServletRequest request)
	throws ServletException{
		try {

			String password=user.getPassword();
			user.setPassword(bCryptPasswordEncoder.encode(password));
			List<Role> roles=new ArrayList<>();
			roles.add(roleRepository.findById(2).get());
			user.setRoles(roles);
			userRepository.save(user);
			request.login(user.getEmail(), password);
			return "redirect:/";
		} catch (Exception e) {
		System.out.println(e);
		return "redirect:/register";
		}
		
	}
	
}
