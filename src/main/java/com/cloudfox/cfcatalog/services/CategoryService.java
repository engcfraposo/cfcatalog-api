package com.cloudfox.cfcatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudfox.cfcatalog.dto.CategoryDTO;
import com.cloudfox.cfcatalog.entities.Category;
import com.cloudfox.cfcatalog.repositories.CategoryRepository;
import com.cloudfox.cfcatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		return list
				.stream()
				.map(category -> new CategoryDTO(category))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> category = repository.findById(id);
		Category entity = category
				.orElseThrow(() -> new EntityNotFoundException("Category not found!"));
		return new CategoryDTO(entity);
	}
	
}
