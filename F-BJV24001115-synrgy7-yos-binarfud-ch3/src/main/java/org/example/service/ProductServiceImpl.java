package org.example.service;

import org.example.model.Product;
import org.example.repository.ProductRepositoryImpl;
import org.example.repository.interfaces.ProductRepository;
import org.example.service.interfaces.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductServiceImpl implements ProductService {
    private static ProductService productService;

    private final ProductRepository productRepository;

    private ProductServiceImpl() {
        productRepository = ProductRepositoryImpl.getInstance();
    }

    public static ProductService getInstance() {
         if (productService == null) {
                productService = new ProductServiceImpl();
          }
          return productService;
    }

    @Override
    public boolean add(Product product) {
        return productRepository.add(product);
    }

    @Override
    public boolean update(Product product) {
        Optional<Product> existingProduct = productRepository.getById(product.getId());

        if (existingProduct.isPresent()) {
            Product prd = existingProduct.get();
            prd.setName(product.getName());
            prd.setPrice(product.getPrice());
            return productRepository.update(prd);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return productRepository.deleteById(uuid);
    }

    @Override
    public boolean isExist(UUID uuid) {
        return productRepository.getById(uuid).isPresent();
    }

    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    public void clear() {
        productRepository.clear();
    }
}
