package com.sagarboyal.job_platform_api.scrapper.controller;

import com.sagarboyal.job_platform_api.scrapper.payload.dtos.ProviderDTO;
import com.sagarboyal.job_platform_api.scrapper.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/provider")
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;

    @GetMapping
    public ResponseEntity<List<ProviderDTO>> getAllProviders() {
        return ResponseEntity.ok(providerService.getAll());
    }

    @PostMapping
    public ResponseEntity<ProviderDTO> createProvider(@RequestBody ProviderDTO providerDTO) {
        return ResponseEntity.ok(providerService.create(providerDTO));
    }

    @PostMapping("bulk")
    public ResponseEntity<List<ProviderDTO>> addBulkProvider(@RequestBody List<ProviderDTO> dtoList) {
        return ResponseEntity.ok(providerService.addBulk(dtoList));
    }

    @PutMapping
    public ResponseEntity<ProviderDTO> updateProvider(@RequestBody ProviderDTO providerDTO) {
        return ResponseEntity.ok(providerService.update(providerDTO));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updateProvider(@PathVariable Long id) {
        providerService.toggleStatus(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        providerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
