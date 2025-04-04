package org.asha.springboot.service;

import org.asha.springboot.entity.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
