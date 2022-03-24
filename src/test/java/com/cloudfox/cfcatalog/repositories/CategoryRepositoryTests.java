package com.cloudfox.cfcatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.cloudfox.cfcatalog.entities.Category;
import com.cloudfox.cfcatalog.tests.Factory;

@DataJpaTest
public class CategoryRepositoryTests {
	
	@Autowired
	private CategoryRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalCategories;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalCategories = 3L;
	}
	
	@Test
	public void saveShouldPersistWithAutoIncrementWhenIdIsNull(){
		long expectedCountTotalCategories = countTotalCategories + 1;
		Category category = Factory.createCategory();
		category.setId(null);
		category = repository.save(category); 
		Assertions.assertNotNull(category.getId());
		Assertions.assertEquals(expectedCountTotalCategories, category.getId());
	}
	
	@Test
	public void findByIdShouldFindOptionalObjectWhenIdExists(){
		Optional<Category> category = repository.findById(existingId);
		Assertions.assertTrue(category.isPresent());
	}
	
	@Test
	public void findByIdShouldNotFindOptionalObjectWhenIdInvalid(){
		Optional<Category> category = repository.findById(nonExistingId);
		Assertions.assertFalse(category.isPresent());
	}
	
	@Test
	public void deleteByIdShouldDeleteObjectWhenIdExists(){
		repository.deleteById(existingId);
		Optional<Category> category = repository.findById(existingId);
		Assertions.assertFalse(category.isPresent());
	}
	
	@Test
	public void deleteByIdShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist(){
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
	}
}
