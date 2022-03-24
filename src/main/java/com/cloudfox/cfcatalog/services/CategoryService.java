package com.cloudfox.cfcatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloudfox.cfcatalog.dto.CategoryDTO;
import com.cloudfox.cfcatalog.entities.Category;
import com.cloudfox.cfcatalog.repositories.CategoryRepository;
import com.cloudfox.cfcatalog.services.exceptions.DatabaseException;
import com.cloudfox.cfcatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		Page<Category> list = repository.findAll(pageable);
		return list.map(entity -> new CategoryDTO(entity));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj
				.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
		Category entity = repository.getOne(id);
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new CategoryDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID not found! "+ id);
		}
	}

	public void delete(Long id) {
		try {
		repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID not found! "+ id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Ïntegrity violation");
		}
	}
	
	private void copyDtoToEntity(CategoryDTO dto, Category entity) {
		entity.setName(dto.getName());
	}
	
}
