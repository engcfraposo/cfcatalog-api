package com.cloudfox.cfcatalog.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.cloudfox.cfcatalog.dto.CategoryDTO;
import com.cloudfox.cfcatalog.services.CategoryService;
import com.cloudfox.cfcatalog.services.exceptions.DatabaseException;
import com.cloudfox.cfcatalog.services.exceptions.ResourceNotFoundException;
import com.cloudfox.cfcatalog.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CategoryResource.class)
public class CategoryResourceTests {
	
	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private PageImpl<CategoryDTO> page;
	private CategoryDTO categoryDTO;
	private String jsonBody;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private CategoryService service;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		dependentId = 14L;
		categoryDTO = Factory.createCategoryDTO();
		page = new PageImpl<>(List.of(categoryDTO));
		jsonBody = objectMapper.writeValueAsString(categoryDTO);
		
		//GET /categories
		Mockito.when(service.findAllPaged(ArgumentMatchers.any())).thenReturn(page);
		
		//GET /categories/id
		Mockito.when(service.findById(existingId)).thenReturn(categoryDTO);
		Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		
		//PUT /categories/id
		Mockito.when(service.update(existingId, categoryDTO)).thenReturn(categoryDTO);
		Mockito.when(service.update(nonExistingId, categoryDTO)).thenThrow(ResourceNotFoundException.class);
		
		//POST /categories
		Mockito.when(service.insert(categoryDTO)).thenReturn(categoryDTO);
		
		//DELETE /categories
		Mockito.doNothing().when(service).delete(existingId);
		Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
		Mockito.doThrow(DatabaseException.class).when(service).delete(dependentId);

	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/categories")
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content").isArray());
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.pageable").exists());
		result.andExpect(jsonPath("$.last").exists());
		result.andExpect(jsonPath("$.totalPages").exists());
		result.andExpect(jsonPath("$.totalElements").exists());
		result.andExpect(jsonPath("$.first").exists());
		result.andExpect(jsonPath("$.numberOfElements").exists());
		result.andExpect(jsonPath("$.number").exists());
		result.andExpect(jsonPath("$.sort").exists());
		result.andExpect(jsonPath("$.size").exists());
		result.andExpect(jsonPath("$.empty").exists());
	}
	
	@Test
	public void findByIdShouldCategoryWhenIdExist() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/categories/{id}", existingId)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());

	}
	
	@Test
	public void findByIdShouldThrowNotFoundExcepctionWhenIdDoesNotExist() throws Exception {
		
		ResultActions result = mockMvc.perform(get("/categories/{id}", nonExistingId)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.timestamp").exists());
		result.andExpect(jsonPath("$.status").exists());
		result.andExpect(jsonPath("$.error").exists());
		result.andExpect(jsonPath("$.path").exists());
	}
	
	
	@Test
	public void updateShouldCategoryWhenIdExist() throws Exception {
		
		ResultActions result = mockMvc.perform(put("/categories/{id}", existingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
	}
	
	@Test
	public void updateShouldThrowNotFoundExcepctionWhenIdDoesNotExistt() throws Exception {
		
		ResultActions result = mockMvc.perform(put("/categories/{id}", nonExistingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.timestamp").exists());
		result.andExpect(jsonPath("$.status").exists());
		result.andExpect(jsonPath("$.error").exists());
		result.andExpect(jsonPath("$.path").exists());
	}
	
	@Test
	public void insertShouldReturnCategoryDTOCreated() throws Exception {
		
		ResultActions result = mockMvc.perform(post("/categories")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		
	}
	
	@Test
	public void deleteShouldCategoryWhenExist() throws Exception {
		
		ResultActions result = mockMvc.perform(delete("/categories/{id}", existingId)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNoContent());
		
	}
	
	@Test
	public void deleteShouldThrowNotFoundExcepctionWhenIdDoesNotExist() throws Exception {
		
		ResultActions result = mockMvc.perform(delete("/categories/{id}", nonExistingId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
		result.andExpect(jsonPath("$.timestamp").exists());
		result.andExpect(jsonPath("$.status").exists());
		result.andExpect(jsonPath("$.error").exists());
		result.andExpect(jsonPath("$.path").exists());
	}
	
	@Test
	public void deleteShouldThrowDatabaseExcepctionWhenIdDoesNotExist() throws Exception {
		
		ResultActions result = mockMvc.perform(delete("/categories/{id}", dependentId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.timestamp").exists());
		result.andExpect(jsonPath("$.status").exists());
		result.andExpect(jsonPath("$.error").exists());
		result.andExpect(jsonPath("$.path").exists());
	}
}
