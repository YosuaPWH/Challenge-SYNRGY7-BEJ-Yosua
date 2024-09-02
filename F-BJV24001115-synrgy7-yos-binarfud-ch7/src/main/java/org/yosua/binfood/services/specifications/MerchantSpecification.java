package org.yosua.binfood.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.yosua.binfood.model.entity.Merchant;

public class MerchantSpecification {

    public static Specification<Merchant> nameContains(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Merchant> locationContains(String location) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("location"), "%" + location + "%");
    }

    public static Specification<Merchant> isOpen(boolean open) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("open"), open);
    }

}
