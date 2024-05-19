package org.example.repository.interfaces;

import org.example.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepository {
    boolean add(Product product);
    Product getById(UUID uuid);
    boolean deleteById(UUID uuid);
    List<Product> getAll();
}
