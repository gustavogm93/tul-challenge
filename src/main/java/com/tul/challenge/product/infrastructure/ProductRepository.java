package com.tul.challenge.product.infrastructure;

import com.tul.challenge.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository  extends JpaRepository<Product, UUID> {}