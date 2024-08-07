package org.yosua.binfood.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yosua.binfood.model.dto.request.ProductRequest;
import org.yosua.binfood.model.dto.response.BaseResponse;
import org.yosua.binfood.model.dto.response.ProductResponse;
import org.yosua.binfood.services.impl.ProductServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/product")
@PreAuthorize("hasAnyRole('USER', 'MERCHANT')")
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getAll() {
        List<ProductResponse> allProduct = productService.getAll();

        BaseResponse<List<ProductResponse>> response = BaseResponse.<List<ProductResponse>>builder()
                .success(true)
                .data(allProduct)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double priceGreater,
            @RequestParam(required = false) Double priceLess
    ) {
        List<ProductResponse> products = productService.getAll(name, priceGreater, priceLess);

        BaseResponse<List<ProductResponse>> response = BaseResponse.<List<ProductResponse>>builder()
                .success(true)
                .data(products)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductResponse>> getById(@PathVariable String id) {
        ProductResponse product = productService.getById(id);

        BaseResponse<ProductResponse> response = BaseResponse.<ProductResponse>builder()
                .success(true)
                .data(product)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.create(request);

        BaseResponse<ProductResponse> response = BaseResponse.<ProductResponse>builder()
                .success(true)
                .data(product)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductResponse>> update(@PathVariable String id, @Valid @RequestBody ProductRequest request) {
        ProductResponse product = productService.update(id, request);

        BaseResponse<ProductResponse> response = BaseResponse.<ProductResponse>builder()
                .success(true)
                .data(product)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable String id) {
        productService.delete(id);

        BaseResponse<String> response = BaseResponse.<String>builder()
                .success(true)
                .message("Product deleted")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
