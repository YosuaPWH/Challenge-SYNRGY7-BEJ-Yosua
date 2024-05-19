package org.example.repository.interfaces;

import org.example.model.Merchant;
import org.example.model.dto.AddMerchantRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MerchantRepository {
    boolean add(AddMerchantRequest addMerchantRequest);

    // Check if merchant with the same name already exists
    boolean isExistByName(String name);

    // Find all merchant that have the same name in their merchant name
    List<Merchant> findByName(String name);

    // Check if merchant with the same location already exists
    boolean isExistByLocation(String location);

    // Find all merchant that have the same location in their merchant location
    List<Merchant> findByLocation(String location);
    Optional<Merchant> getById(UUID uuid);
    boolean deleteById(UUID uuid);
}
