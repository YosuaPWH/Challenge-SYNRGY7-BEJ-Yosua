package org.yosua.binfood.services;

import org.yosua.binfood.model.dto.request.MerchantRequest;
import org.yosua.binfood.model.dto.response.MerchantResponse;

import java.util.List;

public interface MerchantService {
    MerchantResponse create(MerchantRequest request);
    List<MerchantResponse> getAll(String nameFilter, String locationFilter, Boolean openFilter);
    MerchantResponse getById(String id);
    MerchantResponse updateStatus(String id);
    MerchantResponse update(String id, MerchantRequest request);
    void delete(String id);
}
