package org.example.service.interfaces;

import org.example.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    boolean add(Product product);
    boolean update(Product product);
    boolean deleteById(UUID uuid);
    boolean isExist(UUID uuid);
    List<Product> getAll();

    void clear();
}
