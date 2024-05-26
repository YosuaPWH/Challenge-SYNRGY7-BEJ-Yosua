package org.yosua.binfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yosua.binfood.model.request.ProductRequest;
import org.yosua.binfood.model.response.ApiResponse;
import org.yosua.binfood.model.response.ProductResponse;
import org.yosua.binfood.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
@PreAuthorize("hasAnyRole('USER', 'MERCHANT')")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll() {
        List<ProductResponse> allProduct = productService.getAll();
        return ApiResponse.<List<ProductResponse>>builder()
                .success(true)
                .data(allProduct)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable String id) {
        ProductResponse product = productService.getById(id);
        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .data(product)
                .build();
    }

    @PostMapping
    public ApiResponse<ProductResponse> create(@RequestBody ProductRequest request) {
        ProductResponse product = productService.create(request);
        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .data(product)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable String id, @RequestBody ProductRequest request) {
        ProductResponse product = productService.update(id, request);
        return ApiResponse.<ProductResponse>builder()
                .success(true)
                .data(product)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        productService.delete(id);
        return ApiResponse.<String>builder()
                .success(true)
                .message("Product deleted")
                .build();
    }
}
