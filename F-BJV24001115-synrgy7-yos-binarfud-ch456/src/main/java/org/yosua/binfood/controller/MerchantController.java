package org.yosua.binfood.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yosua.binfood.model.dto.request.MerchantRequest;
import org.yosua.binfood.model.dto.response.BaseResponse;
import org.yosua.binfood.model.dto.response.MerchantResponse;
import org.yosua.binfood.services.impl.MerchantServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/merchant")
@PreAuthorize("hasRole('MERCHANT')")
public class MerchantController {

    private final MerchantServiceImpl merchantService;

    @Autowired
    public MerchantController(MerchantServiceImpl merchantService) {
        this.merchantService = merchantService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<MerchantResponse>> create(@Valid @RequestBody MerchantRequest request) {
        MerchantResponse merchantResponse = merchantService.create(request);

        BaseResponse<MerchantResponse> response = BaseResponse.<MerchantResponse>builder()
                .success(true)
                .data(merchantResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(path = "/search")
    public ResponseEntity<BaseResponse<Page<MerchantResponse>>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Boolean open,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<MerchantResponse> merchantResponses = merchantService.getAll(name, location, open, page, size);

        BaseResponse<Page<MerchantResponse>> response = BaseResponse.<Page<MerchantResponse>>builder()
                .success(true)
                .data(merchantResponses)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MerchantResponse>> get(@PathVariable String id) {
        MerchantResponse merchantResponse = merchantService.getById(id);

        BaseResponse<MerchantResponse> response = BaseResponse.<MerchantResponse>builder()
                .success(true)
                .data(merchantResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<MerchantResponse>> update(@PathVariable String id, @Valid @RequestBody MerchantRequest request) {
        MerchantResponse merchantResponse = merchantService.update(id, request);

        BaseResponse<MerchantResponse> response = BaseResponse.<MerchantResponse>builder()
                .success(true)
                .data(merchantResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BaseResponse<MerchantResponse>> updateStatus(@PathVariable String id) {
        MerchantResponse merchantResponse = merchantService.updateStatus(id);

        BaseResponse<MerchantResponse> response = BaseResponse.<MerchantResponse>builder()
                .success(true)
                .data(merchantResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable String id) {
        merchantService.delete(id);

        BaseResponse<String> response = BaseResponse.<String>builder()
                .success(true)
                .message("Merchant deleted")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
