package org.yosua.binfood.services;

import org.springframework.data.domain.Page;
import org.yosua.binfood.model.dto.request.MerchantRequest;
import org.yosua.binfood.model.dto.response.MerchantResponse;

public interface MerchantService {
    MerchantResponse create(MerchantRequest request);
    Page<MerchantResponse> getAll(String nameFilter, String locationFilter, Boolean openFilter, int page, int size);
    MerchantResponse getById(String id);
    MerchantResponse updateStatus(String id);
    MerchantResponse update(String id, MerchantRequest request);
    void delete(String id);
}
