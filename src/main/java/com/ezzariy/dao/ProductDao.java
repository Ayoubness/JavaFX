package com.ezzariy.dao;

import com.ezzariy.model.Product;

import java.util.List;

public interface ProductDao {

    void addProduct(Product product);

    void deleteProductById(long id);

    Product findProductById(long id);

    List<Product> findAllProducts();

    List<Product> findAllProductsByDesignation(String designation);
}
