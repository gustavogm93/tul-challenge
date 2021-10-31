package com.tul.challenge.product.services;

import com.tul.challenge.product.repository.ProductRepository;
import com.tul.challenge.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());
        if (null == productDB){
            return null;
        }
        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setPrice(product.getPrice());
        productDB.setSKU(product.getSKU());
        return productRepository.save(productDB);
    }

    @Override
    public Product deleteProduct(UUID id) {
        Product productDB = getProduct(id);
        if (null == productDB){
            return null;
        }
        return productRepository.save(productDB);
    }
}