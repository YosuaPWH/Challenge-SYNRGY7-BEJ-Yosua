package org.example.service;

import org.example.repository.ProductRepositoryImpl;
import org.example.repository.interfaces.ProductRepository;
import org.example.service.interfaces.ProductService;

public class ProductServiceImpl implements ProductService {
    private static ProductService productService;

    private ProductRepository productRepository;

    private ProductServiceImpl() {
        productRepository = ProductRepositoryImpl.getInstance();
    }

    static ProductService getInstance() {
         if (productService == null) {
                productService = new ProductServiceImpl();
          }
          return productService;
    }


}
