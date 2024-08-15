package org.yosua.binfood.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.yosua.binfood.model.entity.Merchant;
import org.yosua.binfood.model.dto.request.MerchantRequest;
import org.yosua.binfood.model.dto.response.MerchantResponse;
import org.yosua.binfood.repositories.MerchantRepository;
import org.yosua.binfood.repositories.ProductRepository;
import org.yosua.binfood.services.MerchantService;
import org.yosua.binfood.services.specifications.MerchantSpecification;
import org.yosua.binfood.utils.Constants;

import java.util.List;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final ProductRepository productRepository;

    @Autowired
    public MerchantServiceImpl(MerchantRepository merchantRepository, ProductRepository productRepository) {
        this.merchantRepository = merchantRepository;
        this.productRepository = productRepository;
    }

    @Override
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

    @Override
    public Page<MerchantResponse> getAll(String nameFilter, String locationFilter, Boolean openFilter, int page, int size) {
        Specification<Merchant> spec = getMerchantSpecification(nameFilter, locationFilter, openFilter);

        Pageable pageable = PageRequest.of(page, size);

        Page<Merchant> merchants = merchantRepository.findAll(spec, pageable);

        return merchants.map(merchant -> MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .location(merchant.getLocation())
                .open(merchant.isOpen())
                .build());
    }

    private static Specification<Merchant> getMerchantSpecification(String nameFilter, String locationFilter, Boolean openFilter) {
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
        return spec;
    }

    @Override
    public MerchantResponse getById(String id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.merchantNotFoundMessage(id)));

        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .location(merchant.getLocation())
                .open(merchant.isOpen())
                .build();
    }

    @Override
    public MerchantResponse updateStatus(String id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.merchantNotFoundMessage(id)));

        merchant.setOpen(!merchant.isOpen());
        merchantRepository.save(merchant);

        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .location(merchant.getLocation())
                .open(merchant.isOpen())
                .build();
    }

    @Override
    public MerchantResponse update(String id, MerchantRequest request) {
        Merchant merchant = getMerchant(id, request);

        return MerchantResponse.builder()
                .id(merchant.getId())
                .name(merchant.getName())
                .location(merchant.getLocation())
                .open(merchant.isOpen())
                .build();
    }

    private Merchant getMerchant(String id, MerchantRequest request) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(Constants.merchantNotFoundMessage(id)));

        if (merchantRepository.existsByName(request.getName()) && !merchant.getName().equals(request.getName())) {
            throw new IllegalArgumentException("Merchant with name " + request.getName() + " already exists");
        }

        merchant.setName(request.getName());
        merchant.setLocation(request.getLocation());
        merchantRepository.save(merchant);
        return merchant;
    }

    @Override
    public void delete(String id) {
        if (!merchantRepository.existsById(id)) {
            throw new IllegalArgumentException(Constants.merchantNotFoundMessage(id));
        }

        productRepository.deleteAllByMerchantId(UUID.fromString(id));

        merchantRepository.deleteById(id);
    }
}
