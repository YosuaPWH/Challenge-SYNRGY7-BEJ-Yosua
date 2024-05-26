package org.yosua.binfood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yosua.binfood.model.entity.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, String> {
    void deleteAllByMerchantId(UUID merchantId);
}
