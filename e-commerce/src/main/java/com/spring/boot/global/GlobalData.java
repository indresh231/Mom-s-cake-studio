package com.spring.boot.global;


import java.util.*;

import com.spring.boot.model.Product;

public class GlobalData {

	public static List<Product> cart;
	static {
		cart=new ArrayList<Product>();
		
	}
}
