package com.cloudfox.cfcatalog.resolvers;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudfox.cfcatalog.dto.CategoryDTO;
import com.cloudfox.cfcatalog.services.CategoryService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

public class CategoryMutation implements GraphQLMutationResolver {
	@Autowired
	private CategoryService service;
	
	@SuppressWarnings("null")
	public CategoryDTO insert(Long id, String name){
		String getName = name;
		CategoryDTO dto = null;
		dto.setName(getName);
		dto = service.insert(dto);	
		return dto;
	}
	
	@SuppressWarnings("null")
	public CategoryDTO update(Long id, String name){
		String getName = name;
		CategoryDTO dto = null;
		dto.setName(getName);
		dto = service.update(id,dto);
		return dto;
	}
	
	public String delete(Long id){
		service.delete(id);
		String response = "ok";
		return response;
	}
}
