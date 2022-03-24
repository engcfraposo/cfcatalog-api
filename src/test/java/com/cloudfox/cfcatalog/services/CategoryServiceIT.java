package com.cloudfox.cfcatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.cloudfox.cfcatalog.dto.CategoryDTO;
import com.cloudfox.cfcatalog.repositories.CategoryRepository;
import com.cloudfox.cfcatalog.services.exceptions.ResourceNotFoundException;
import com.cloudfox.cfcatalog.tests.Factory;

@SpringBootTest
@Transactional
public class CategoryServiceIT {
	
	@Autowired
	private CategoryService service;
	
	@Autowired
	private CategoryRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalCategories;
	
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 3L;
		nonExistingId = 1000L;
		countTotalCategories = 3L;		
	}
	
	@Test
	public void deleteShouldCreateIndependentCategoryDTOAndDeleteResourceWhenIdExists() {
		CategoryDTO categoryDTO = Factory.createCategoryDTO();
		service.insert(categoryDTO);
		service.delete(4L);
		Assertions.assertEquals(existingId, repository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class,() -> {
			service.delete(nonExistingId);
		});
	}
	
	@Test
	public void findAllPagedShouldReturnPageWhenPage0Size10() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		Page<CategoryDTO> result = service.findAllPaged(pageRequest);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalCategories, result.getTotalElements());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExists() {
		
		PageRequest pageRequest = PageRequest.of(50, 10);
		Page<CategoryDTO> result = service.findAllPaged(pageRequest);
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void findAllPagedShouldReturnOrderedPageWhenSortByName() {
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
		Page<CategoryDTO> result = service.findAllPaged(pageRequest);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalCategories, result.getTotalElements());
		Assertions.assertEquals("Books", result.getContent().get(0).getName());
		Assertions.assertEquals("Computers", result.getContent().get(1).getName());
		Assertions.assertEquals("Electronics", result.getContent().get(2).getName());

	}
}
