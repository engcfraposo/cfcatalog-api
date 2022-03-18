package com.cloudfox.cfcatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudfox.cfcatalog.entities.Category;
import com.cloudfox.cfcatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public List<Category> findAll(){
		return repository.findAll();
	}
	
}
