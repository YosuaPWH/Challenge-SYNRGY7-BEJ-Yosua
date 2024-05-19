package org.example.repository;

import org.example.model.Merchant;
import org.example.model.dto.AddMerchantRequest;
import org.example.repository.interfaces.MerchantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MerchantRepositoryImpl implements MerchantRepository {

    private static MerchantRepository merchantRepository;
    private final ArrayList<Merchant> merchants;

    private MerchantRepositoryImpl() {
        this.merchants = new ArrayList<>();
    }

    public static MerchantRepository getInstance() {
        if (merchantRepository == null) {
            merchantRepository = new MerchantRepositoryImpl();
        }
        return merchantRepository;
    }


    @Override
    public boolean add(AddMerchantRequest addMerchantRequest) {
        if (isExistByName(addMerchantRequest.getName())) {
            return false;
        }
        Merchant merchant = new Merchant(
                UUID.randomUUID(),
                addMerchantRequest.getName(),
                addMerchantRequest.getLocation(),
                false
        );

        return merchants.add(merchant);
    }

    @Override
    public boolean isExistByName(String name) {
        return merchants.stream()
                .anyMatch(merchant -> merchant.getName().equals(name));
    }

    @Override
    public List<Merchant> findByName(String name) {
        return merchants.stream()
                .filter(merchant -> merchant.getName().contains(name))
                .toList();
    }

    @Override
    public boolean isExistByLocation(String location) {
        return merchants.stream()
                .anyMatch(merchant -> merchant.getLocation().equals(location));
    }

    @Override
    public List<Merchant> findByLocation(String location) {
        return merchants.stream()
                .filter(merchant -> merchant.getLocation().contains(location))
                .toList();
    }

    @Override
    public Merchant getById(UUID uuid) {
        return merchants.stream()
                .filter(merchant -> merchant.getId().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean deleteById(UUID uuid) {
        return merchants.removeIf(merchant -> merchant.getId().equals(uuid));
    }
}
