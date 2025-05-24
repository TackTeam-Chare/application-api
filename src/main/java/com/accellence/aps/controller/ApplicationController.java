package com.accellence.aps.controller;

import com.accellence.aps.dto.ApplicationRequestDTO;
import com.accellence.aps.dto.ApplicationResponseDTO;
import com.accellence.aps.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    // Constructor injection
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>> getAllApplications() {
        List<ApplicationResponseDTO> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{appId}")
    public ResponseEntity<ApplicationResponseDTO> getApplicationById(@PathVariable UUID appId) {
        ApplicationResponseDTO application = applicationService.getApplicationById(appId);
        return ResponseEntity.ok(application);
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> createApplication(
            @Valid @RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO createdApplication = applicationService.createApplication(applicationRequestDTO);
        return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
    }

    @PutMapping("/{appId}")
    public ResponseEntity<ApplicationResponseDTO> updateApplication(
            @PathVariable UUID appId,
            @Valid @RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO updatedApplication = applicationService.updateApplication(appId, applicationRequestDTO);
        return ResponseEntity.ok(updatedApplication);
    }

    @DeleteMapping("/{appId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable UUID appId) {
        applicationService.deleteApplication(appId);
        return ResponseEntity.noContent().build();
    }
}
