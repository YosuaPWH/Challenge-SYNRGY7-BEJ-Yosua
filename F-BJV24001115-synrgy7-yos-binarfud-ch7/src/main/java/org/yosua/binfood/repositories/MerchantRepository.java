package org.yosua.binfood.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.yosua.binfood.model.entity.Merchant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, String>, JpaSpecificationExecutor<Merchant> {
    Optional<Merchant> findFirstByName(String name);
    boolean existsByName(String name);
    List<Merchant> findAllByNameContains(String name);
    List<Merchant> findAllByLocationContains(String location);
    List<Merchant> findAllByOpen(boolean open);
    @Query("SELECT m FROM Merchant m WHERE m.isActive = false")
    List<Merchant> findSoftDeleted();

    @Procedure(procedureName = "getmerchantbyuserid")
    List<Merchant> getMerchantByUserId(@Param("userId") UUID userId);
}
