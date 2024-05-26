package org.yosua.binfood.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class MerchantResponse {
    private UUID id;
    private String name;
    private String location;
    private boolean open;
}
