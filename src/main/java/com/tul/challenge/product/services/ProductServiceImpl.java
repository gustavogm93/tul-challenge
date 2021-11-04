package com.tul.challenge.product.services;

import com.tul.challenge.config.exception.NotFoundException;
import com.tul.challenge.config.exception.ProductNotFoundException;
import com.tul.challenge.product.repository.ProductRepository;
import com.tul.challenge.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl  implements ProductService {


    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(UUID id) {
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, Product productRequest) {
        Product productDB = getProduct(id);

        productDB.updateProduct(productRequest);

        return productRepository.save(productDB);
    }

    @Override
    public boolean deleteProduct(UUID id) {
        Product productDB = getProduct(id);

        productRepository.delete(productDB);
        return true;
    }
}