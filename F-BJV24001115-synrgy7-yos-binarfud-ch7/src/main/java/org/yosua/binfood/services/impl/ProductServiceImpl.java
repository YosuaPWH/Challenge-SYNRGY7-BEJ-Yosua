package org.yosua.binfood.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.yosua.binfood.model.entity.Merchant;
import org.yosua.binfood.model.entity.Product;
import org.yosua.binfood.model.dto.request.ProductRequest;
import org.yosua.binfood.model.dto.response.ProductResponse;
import org.yosua.binfood.repositories.MerchantRepository;
import org.yosua.binfood.repositories.ProductRepository;
import org.yosua.binfood.services.ProductService;
import org.yosua.binfood.services.specifications.ProductSpecification;
import org.yosua.binfood.utils.Constants;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, MerchantRepository merchantRepository) {
        this.productRepository = productRepository;
        this.merchantRepository = merchantRepository;
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll().stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .merchantId(product.getMerchant().getId())
                        .build())
                .toList();
    }

    @Override
    public List<ProductResponse> getAll(String nameFilter, Double priceGreaterFilter, Double priceLessFilter) {
        Specification<Product> spec = getProductSpecification(nameFilter, priceGreaterFilter, priceLessFilter);

        List<Product> products = productRepository.findAll(spec);

        return products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .merchantId(product.getMerchant().getId())
                        .build())
                .toList();
    }

    private static Specification<Product> getProductSpecification(String nameFilter, Double priceGreaterFilter, Double priceLessFilter) {
        Specification<Product> spec = Specification.where(null);

        if (nameFilter != null && !nameFilter.isBlank()) {
            spec = spec.and(ProductSpecification.nameContains(nameFilter));
        }

        if (priceGreaterFilter != null) {
            spec = spec.and(ProductSpecification.priceGreaterThan(priceGreaterFilter));
        }

        if (priceLessFilter != null) {
            spec = spec.and(ProductSpecification.priceLessThan(priceLessFilter));
        }
        return spec;
    }

    @Override
    public ProductResponse getById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.productNotFoundMessage(id)));

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .merchantId(product.getMerchant().getId())
                .build();
    }

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = getProductByMerchantId(request);

        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .merchantId(product.getMerchant().getId())
                .build();
    }

    private Product getProductByMerchantId(ProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException(Constants.productNameAlreadyExistsMessage(request.getName()));
        }

        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(() -> new IllegalArgumentException(Constants.merchantNotFoundMessage(request.getMerchantId())));

        return Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .merchant(merchant)
                .build();
    }

    @Override
    public ProductResponse update(String id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.productNotFoundMessage(id)));

        if (productRepository.existsByName(request.getName()) && !product.getName().equals(request.getName())) {
            throw new IllegalArgumentException(Constants.productNameAlreadyExistsMessage(request.getName()));
        }

        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(() -> new IllegalArgumentException(Constants.merchantNotFoundMessage(request.getMerchantId())));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setMerchant(merchant);

        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .merchantId(product.getMerchant().getId())
                .build();
    }

    @Override
    public void delete(String id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException(Constants.productNotFoundMessage(id));
        }

        productRepository.deleteById(id);
    }
}
