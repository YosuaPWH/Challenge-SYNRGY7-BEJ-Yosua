package org.example.repository;

import org.example.model.Product;
import org.example.repository.interfaces.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductRepositoryImpl implements ProductRepository {
    private static ProductRepository productRepository;
    private final ArrayList<Product> products;

    private ProductRepositoryImpl() {
        products = new ArrayList<>();
    }

    public static ProductRepository getInstance() {
        if (productRepository == null) {
            productRepository = new ProductRepositoryImpl();
        }
        return productRepository;
    }

    @Override
    public boolean add(Product product) {
        if (product.getId() == null) {
            product.setId(UUID.randomUUID());
        }
        return products.add(product);
    }

    @Override
    public Optional<Product> getById(UUID uuid) {
        return products.stream()
                .filter(product -> product.getId().equals(uuid))
                .findFirst();
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return products.removeIf(product -> product.getId().equals(uuid));
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public boolean update(Product product) {
        Optional<Product> existingProduct = getById(product.getId());

        if (existingProduct.isEmpty()) {
            return false;
        }

        products.set(products.indexOf(existingProduct.get()), product);
        return true;
    }
}
