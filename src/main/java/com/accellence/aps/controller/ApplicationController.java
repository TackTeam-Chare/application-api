package com.accellence.aps.controller;

import com.accellence.aps.dto.ApplicationRequestDTO;
import com.accellence.aps.dto.ApplicationResponseDTO;
import com.accellence.aps.dto.ResponseWrapper;
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
    public ResponseEntity<ResponseWrapper<List<ApplicationResponseDTO>>> getAllApplications() {
        List<ApplicationResponseDTO> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(ResponseWrapper.success("Applications retrieved successfully", applications));
    }

    @GetMapping("/{appId}")
    public ResponseEntity<ResponseWrapper<ApplicationResponseDTO>> getApplicationById(@PathVariable UUID appId) {
        ApplicationResponseDTO application = applicationService.getApplicationById(appId);
        return ResponseEntity.ok(ResponseWrapper.success("Application retrieved successfully", application));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<ApplicationResponseDTO>> createApplication(
            @Valid @RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO createdApplication = applicationService.createApplication(applicationRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseWrapper.success("Application created successfully", createdApplication));
    }

    @PutMapping("/{appId}")
    public ResponseEntity<ResponseWrapper<ApplicationResponseDTO>> updateApplication(
            @PathVariable UUID appId,
            @Valid @RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO updatedApplication = applicationService.updateApplication(appId, applicationRequestDTO);
        return ResponseEntity.ok(ResponseWrapper.success("Application updated successfully", updatedApplication));
    }

    @DeleteMapping("/{appId}")
    public ResponseEntity<ResponseWrapper<Void>> deleteApplication(@PathVariable UUID appId) {
        applicationService.deleteApplication(appId);
        return ResponseEntity
                .ok(ResponseWrapper.success("Application deleted successfully", null));
    }

        @DeleteMapping
        public ResponseEntity<ResponseWrapper<Void>> deleteAllApplications(
                @RequestParam(value = "confirm", defaultValue = "false") boolean confirm) {
            if (!confirm) {
                return ResponseEntity
                        .badRequest()
                        .body(ResponseWrapper.error("Please confirm this action by adding '?confirm=true' to your request"));
            }
            applicationService.deleteAllApplications();
            return ResponseEntity
                    .ok(ResponseWrapper.success("All applications deleted successfully", null));
        }
}
