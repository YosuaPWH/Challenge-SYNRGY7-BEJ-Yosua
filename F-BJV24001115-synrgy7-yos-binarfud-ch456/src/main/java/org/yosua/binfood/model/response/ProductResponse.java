package org.yosua.binfood.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ProductResponse {
    private UUID id;
    private String name;
    private Double price;
    private UUID merchantId;
}
