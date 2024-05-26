package org.yosua.binfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yosua.binfood.model.request.MerchantRequest;
import org.yosua.binfood.model.response.ApiResponse;
import org.yosua.binfood.model.response.MerchantResponse;
import org.yosua.binfood.services.MerchantService;

import java.util.List;

@RestController
@RequestMapping("/merchant")
@PreAuthorize("hasRole('MERCHANT')")
public class MerchantController {

    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    public ApiResponse<MerchantResponse> create(@RequestBody MerchantRequest request) {
        MerchantResponse merchantResponse = merchantService.create(request);
        return ApiResponse.<MerchantResponse>builder()
                .success(true)
                .data(merchantResponse)
                .build();
    }

    @PostMapping(path = "/search")
    public ApiResponse<List<MerchantResponse>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean open
    ) {
        List<MerchantResponse> merchantResponses = merchantService.getAll(name, location, open);
        return ApiResponse.<List<MerchantResponse>>builder()
                .success(true)
                .data(merchantResponses)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MerchantResponse> get(@PathVariable String id) {
        MerchantResponse merchantResponse = merchantService.getById(id);
        return ApiResponse.<MerchantResponse>builder()
                .success(true)
                .data(merchantResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MerchantResponse> update(@PathVariable String id, @RequestBody MerchantRequest request) {
        MerchantResponse merchantResponse = merchantService.update(id, request);
        return ApiResponse.<MerchantResponse>builder()
                .success(true)
                .data(merchantResponse)
                .build();
    }

    @PutMapping("/{id}/status")
    public ApiResponse<MerchantResponse> updateStatus(@PathVariable String id) {
        MerchantResponse merchantResponse = merchantService.updateStatus(id);
        return ApiResponse.<MerchantResponse>builder()
                .success(true)
                .data(merchantResponse)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {
        merchantService.delete(id);
        return ApiResponse.<String>builder()
                .success(true)
                .message("Merchant deleted")
                .build();
    }
}
