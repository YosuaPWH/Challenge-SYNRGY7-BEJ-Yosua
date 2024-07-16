package org.yosua.binfood.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductRequest {
    @JsonProperty("merchant_id")
    private String merchantId;
    private String name;
    private Double price;
}
