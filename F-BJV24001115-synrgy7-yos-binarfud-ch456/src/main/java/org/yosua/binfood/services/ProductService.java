package org.yosua.binfood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yosua.binfood.model.entity.Merchant;
import org.yosua.binfood.model.entity.Product;
import org.yosua.binfood.model.request.ProductRequest;
import org.yosua.binfood.model.response.ProductResponse;
import org.yosua.binfood.repositories.MerchantRepository;
import org.yosua.binfood.repositories.ProductRepository;
import org.yosua.binfood.utils.Constants;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, MerchantRepository merchantRepository) {
        this.productRepository = productRepository;
        this.merchantRepository = merchantRepository;
    }

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

    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException(Constants.productNameAlreadyExistsMessage(request.getName()));
        }

        Merchant merchant = merchantRepository.findById(request.getMerchantId())
                .orElseThrow(() -> new IllegalArgumentException(Constants.merchantNotFoundMessage(request.getMerchantId())));

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .merchant(merchant)
                .build();

        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .merchantId(product.getMerchant().getId())
                .build();
    }

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

    public void delete(String id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException(Constants.productNotFoundMessage(id));
        }

        productRepository.deleteById(id);
    }
}
