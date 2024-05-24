package org.example.service;

import org.example.model.Merchant;
import org.example.model.Product;
import org.example.service.interfaces.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = ProductServiceImpl.getInstance();
        productService.clear();
    }

    @Test
    void testAddProduct_Success() {
        Merchant merchant = new Merchant(UUID.randomUUID(), "merchant", "loc", true);
        Product product = new Product(UUID.randomUUID(), "product", 100, merchant);
        boolean isAdded = productService.add(product);
        Assertions.assertTrue(isAdded);
        Assertions.assertTrue(productService.isExist(product.getId()));
    }

    @Test
    void testUpdateProduct_Success() {
        Merchant merchant = new Merchant(UUID.randomUUID(), "merchant", "loc", true);
        UUID idProduct = UUID.randomUUID();
        Product product = new Product(idProduct, "Test Product", 9.99, merchant);
        productService.add(product);

        Product updatedProduct = new Product(idProduct, "Updated Product", 19.99, merchant);
        boolean isUpdated = productService.update(updatedProduct);
        Assertions.assertTrue(isUpdated);

        List<Product> products = productService.getAll();
        Assertions.assertEquals(1, products.size());
        Assertions.assertEquals(updatedProduct.getName(), products.get(0).getName());
        Assertions.assertEquals(updatedProduct.getPrice(), products.get(0).getPrice());
    }

    @Test
    void testUpdateNonExistingProduct_Failed() {
        Merchant merchant = new Merchant(UUID.randomUUID(), "merchant", "loc", true);
        Product nonExistingProduct = new Product(UUID.randomUUID(), "Non-Existing Product", 19.99, merchant);
        boolean isUpdated = productService.update(nonExistingProduct);
        Assertions.assertFalse(isUpdated);
    }

    @Test
    void testDeleteProduct_Success() {
        Merchant merchant = new Merchant(UUID.randomUUID(), "merchant", "loc", true);
        Product product = new Product(UUID.randomUUID(), "Test Product", 9.99, merchant);
        productService.add(product);
        Assertions.assertTrue(productService.isExist(product.getId()));

        boolean isDeleted = productService.deleteById(product.getId());
        Assertions.assertTrue(isDeleted);
        Assertions.assertFalse(productService.isExist(product.getId()));
    }

    @Test
    void testDeleteNonExistingProduct_Failed() {
        UUID nonExistingId = UUID.randomUUID();
        boolean isDeleted = productService.deleteById(nonExistingId);
        Assertions.assertFalse(isDeleted);
    }

    @Test
    void testGetAllProducts_Success() {
        Merchant merchant = new Merchant(UUID.randomUUID(), "merchant", "loc", true);
        Product product1 = new Product(UUID.randomUUID(), "Product 1", 9.99, merchant);
        Product product2 = new Product(UUID.randomUUID(), "Product 2", 19.99, merchant);
        productService.add(product1);
        productService.add(product2);

        List<Product> products = productService.getAll();
        Assertions.assertEquals(2, products.size());
        Assertions.assertTrue(products.contains(product1));
        Assertions.assertTrue(products.contains(product2));
    }
}