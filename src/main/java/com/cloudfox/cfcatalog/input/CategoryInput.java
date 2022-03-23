package com.cloudfox.cfcatalog.input;

import graphql.schema.GraphQLInputType;

public class CategoryInput implements GraphQLInputType{
	
	public String name;

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
