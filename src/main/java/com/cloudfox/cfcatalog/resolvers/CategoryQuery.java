package com.cloudfox.cfcatalog.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.cloudfox.cfcatalog.dto.CategoryDTO;
import com.cloudfox.cfcatalog.services.CategoryService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;


public class CategoryQuery implements GraphQLQueryResolver {
	@Autowired
	private CategoryService service;
	
	public CategoryDTO findById(Long id){
		CategoryDTO dto = service.findById(id);	
		return dto;
	}
	
	public Page<CategoryDTO> findAll(
			Integer page,
			Integer linesPerPage,
			String orderBy,
			String direction	
		){
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<CategoryDTO> listDTO = service.findAllPaged(pageRequest);	
		return listDTO;
	}
}
