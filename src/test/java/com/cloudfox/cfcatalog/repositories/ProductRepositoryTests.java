package com.cloudfox.cfcatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.cloudfox.cfcatalog.entities.Product;
import com.cloudfox.cfcatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull(){
		long expectedCountTotalProducts = countTotalProducts + 1;
		Product product = Factory.createProduct();
		product.setId(null);
		product = repository.save(product); 
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(expectedCountTotalProducts, product.getId());
	}
	
	@Test
	public void findByIdShouldFindOptionalObjectWhenIdExists(){
		Optional<Product> product = repository.findById(existingId);
		Assertions.assertTrue(product.isPresent());
	}
	
	@Test
	public void findByIdShouldNotFindOptionalObjectWhenIdInvalid(){
		Optional<Product> product = repository.findById(nonExistingId);
		Assertions.assertFalse(product.isPresent());
	}
	
	@Test
	public void deleteByIdShouldDeleteObjectWhenIdExists(){
		repository.deleteById(existingId);
		Optional<Product> product = repository.findById(existingId);
		Assertions.assertFalse(product.isPresent());
	}
	
	@Test
	public void deleteByIdShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist(){
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}
}
