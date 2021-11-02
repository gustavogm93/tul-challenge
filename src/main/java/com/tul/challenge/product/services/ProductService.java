package com.tul.challenge.product.services;

import com.tul.challenge.product.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    public List<Product> listAllProduct();
    public Product getProduct(UUID id);
    public Product createProduct(Product product);
    public Product updateProduct(Product product);
    public boolean deleteProduct(UUID id);
}