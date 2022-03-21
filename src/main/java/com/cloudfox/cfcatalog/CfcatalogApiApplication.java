package com.cloudfox.cfcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cloudfox.cfcatalog.resolvers.CategoryMutation;
import com.cloudfox.cfcatalog.resolvers.CategoryQuery;
import com.coxautodev.graphql.tools.SchemaParser;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;

@SpringBootApplication
public class CfcatalogApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfcatalogApiApplication.class, args);
		GraphQLSchema graphQLSchema =
		        SchemaParser.newParser()
		            .file("swapi.graphqls")
		            .resolvers(new CategoryQuery(), new CategoryMutation())
		            .build()
		            .makeExecutableSchema();
		GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();
		graphQL.execute("{ droid(id: \"2001\") { name } } ");
	}

}
