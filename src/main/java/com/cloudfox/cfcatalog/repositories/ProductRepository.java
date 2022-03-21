package com.cloudfox.cfcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudfox.cfcatalog.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
