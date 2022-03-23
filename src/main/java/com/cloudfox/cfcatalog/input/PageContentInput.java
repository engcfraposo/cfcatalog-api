package com.cloudfox.cfcatalog.input;

import graphql.schema.GraphQLInputType;

public class PageContentInput implements GraphQLInputType{
	
	private String name;
	@Override
	public String getName() {
		return name;
	}
	
	private Integer page;
	private Integer linesPerPage;
	private String orderBy;
	private String direction;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getLinesPerPage() {
		return linesPerPage;
	}
	public void setLinesPerPage(Integer linesPerPage) {
		this.linesPerPage = linesPerPage;
	}
	public String getOrderBy() {
		return orderBy;
	}
	
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	

}
