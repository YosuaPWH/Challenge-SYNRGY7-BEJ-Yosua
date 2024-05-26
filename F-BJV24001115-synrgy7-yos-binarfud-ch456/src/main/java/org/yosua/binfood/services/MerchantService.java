package org.yosua.binfood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.yosua.binfood.model.entity.Merchant;
import org.yosua.binfood.model.request.MerchantRequest;
import org.yosua.binfood.model.response.MerchantResponse;
import org.yosua.binfood.repositories.MerchantRepository;
import org.yosua.binfood.repositories.ProductRepository;
import org.yosua.binfood.services.specifications.MerchantSpecification;
import org.yosua.binfood.utils.Constants;

import java.util.List;
import java.util.UUID;

@Service
public class MerchantService {

    private final MerchantRepository merchantRepository;
    private final ProductRepository productRepository;

    @Autowired
    public MerchantService(MerchantRepository merchantRepository, ProductRepository productRepository) {
        this.merchantRepository = merchantRepository;
        this.productRepository = productRepository;
    }

    public MerchantResponse create(MerchantRequest request) {
        if (merchantRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Merchant with name " + request.getName() + " already exists");
        }

        Merchant merchant = Merchant.builder()
                .name(request.getName())
                .location(request.getLocation())
                .build();

        merchantRepository.save(merchant);

        return MerchantResponse.builder()
                .name(merchant.getName())
                .location(merchant.getLocation())
                .build();
    }

    public List<MerchantResponse> getAll(String nameFilter, String locationFilter, Boolean openFilter) {
        Specification<Merchant> spec = Specification.where(null);

        if (nameFilter != null && !nameFilter.isBlank()) {
            spec = spec.and(MerchantSpecification.nameContains(nameFilter));
        }

        if (locationFilter != null && !locationFilter.isBlank()) {
            spec = spec.and(MerchantSpecification.locationContains(locationFilter));
        }

        if (openFilter != null) {
            spec = spec.and(MerchantSpecification.isOpen(openFilter));
        }

        List<Merchant> merchants = merchantRepository.findAll(spec);

        return merchants.stream()
                .map(merchant -> MerchantResponse.builder()
                        .id(merchant.getId())
                        .name(merchant.getName())
                        .location(merchant.getLocation())
                        .open(merchant.isOpen())
                        .build())
                .toList();
    }

    public MerchantResponse getById(String id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.MERCHANT_NOT_FOUND_MESSAGE(id)));

        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .location(merchant.getLocation())
                .open(merchant.isOpen())
                .build();
    }

    public MerchantResponse updateStatus(String id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.MERCHANT_NOT_FOUND_MESSAGE(id)));

        merchant.setOpen(!merchant.isOpen());
        merchantRepository.save(merchant);

        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .location(merchant.getLocation())
                .open(merchant.isOpen())
                .build();
    }

    public MerchantResponse update(String id, MerchantRequest request) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.MERCHANT_NOT_FOUND_MESSAGE(id)));

        if (merchantRepository.existsByName(request.getName()) && !merchant.getName().equals(request.getName())) {
            throw new IllegalArgumentException("Merchant with name " + request.getName() + " already exists");
        }

        merchant.setName(request.getName());
        merchant.setLocation(request.getLocation());
        merchantRepository.save(merchant);

        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .location(merchant.getLocation())
                .open(merchant.isOpen())
                .build();
    }

    public void delete(String id) {
        if (!merchantRepository.existsById(id)) {
            throw new IllegalArgumentException(Constants.MERCHANT_NOT_FOUND_MESSAGE(id));
        }

        productRepository.deleteAllByMerchantId(UUID.fromString(id));

        merchantRepository.deleteById(id);
    }
}
