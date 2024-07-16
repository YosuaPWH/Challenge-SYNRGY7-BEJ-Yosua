package org.yosua.binfood.services.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.yosua.binfood.model.entity.Product;

public class ProductSpecification {

    public static Specification<Product> nameContains(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    public static Specification<Product> priceGreaterThan(Double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get("price"), price);
    }

    public static Specification<Product> priceLessThan(Double price) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("price"), price);
    }
}
