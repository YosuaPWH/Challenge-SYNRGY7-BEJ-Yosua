package org.yosua.binfood.services;

import org.yosua.binfood.model.dto.request.ProductRequest;
import org.yosua.binfood.model.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();
    List<ProductResponse> getAll(String nameFilter, Double priceGreaterFilter, Double priceLessFilter);
    ProductResponse getById(String id);
    ProductResponse create(ProductRequest request);
    ProductResponse update(String id, ProductRequest request);
    void delete(String id);
}
