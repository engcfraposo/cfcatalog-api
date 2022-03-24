package com.cloudfox.cfcatalog.tests;

import java.time.Instant;

import com.cloudfox.cfcatalog.dto.CategoryDTO;
import com.cloudfox.cfcatalog.dto.ProductDTO;
import com.cloudfox.cfcatalog.entities.Category;
import com.cloudfox.cfcatalog.entities.Product;
import com.cloudfox.cfcatalog.services.exceptions.ResourceNotFoundException;

public class Factory {
	public static Product createProduct() {
		Product product =  new Product(1L, "Phone", "Good Phone", 800.00, "https://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}
	
	public static Category createCategory() {
		return new Category(1L, "Electronics");
	}
	
	public static CategoryDTO createCategoryDTO() {
		Category category = createCategory();
		return new CategoryDTO(category);
	}
	public static ResourceNotFoundException createResourceNotFoundException(long  id) {
		return new ResourceNotFoundException("ID not found! "+ id);
	}
}
