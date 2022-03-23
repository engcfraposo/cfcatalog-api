package com.cloudfox.cfcatalog.resolvers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

import com.cloudfox.cfcatalog.dto.CategoryDTO;
import com.cloudfox.cfcatalog.input.CategoryInput;
import com.cloudfox.cfcatalog.input.PageContentInput;
import com.cloudfox.cfcatalog.services.CategoryService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;


public class CategoryResolver implements GraphQLQueryResolver, GraphQLMutationResolver{
	
	@Autowired
	private CategoryService service;
	
	public Page<CategoryDTO> findAll(PageContentInput input){
		PageRequest pageRequest = PageRequest.of(
				input.getPage(), 
				input.getLinesPerPage(), 
				Direction.valueOf(input.getDirection()), 
				input.getOrderBy()
				);
		Page<CategoryDTO> listDTO = service.findAllPaged(pageRequest);	
		return listDTO;
	}
	
	public CategoryDTO findById(Long id){
		CategoryDTO dto = service.findById(id);	
		return dto;
	}
	
	public CategoryDTO insert(CategoryInput input){
		CategoryDTO dto = null;
		copyInputToDTO(input,dto);
		dto = service.insert(dto);
		return dto;
	}
	
	public CategoryDTO update(Long id, CategoryInput input){
		CategoryDTO dto = null;
		copyInputToDTO(input,dto);
		dto = service.update(id,dto);
		return dto;
	}
	
	private void copyInputToDTO(CategoryInput input, CategoryDTO dto) {
		dto.setName(input.getName());
	}
	
	public String delete(Long id){
		service.delete(id);
		String response = "ok";
		return response;
	}
}
