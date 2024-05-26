package org.yosua.binfood.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MerchantRequest {
    private String name;
    private String location;
}
