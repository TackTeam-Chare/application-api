package com.accellence.aps.controller;

import com.accellence.aps.dto.ApplicationRequestDTO;
import com.accellence.aps.dto.ApplicationResponseDTO;
import com.accellence.aps.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/application")
@Tag(name = "Application", description = "Application management APIs")
public class ApplicationController {
    
    private final ApplicationService applicationService;
    
    // Constructor injection
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    
    @GetMapping
    @Operation(
            operationId = "getAllApplications",
            summary = "Get all applications",
            description = "Retrieves a list of all applications in the system"
    )
    @ApiResponse(responseCode = "200", description = "Applications retrieved successfully")
    public ResponseEntity<List<ApplicationResponseDTO>> getAllApplications() {
        List<ApplicationResponseDTO> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/{appId}")
    @Operation(
            operationId = "getApplicationById",
            summary = "Get application by ID",
            description = "Retrieves a specific application by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Application retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Application not found")
    public ResponseEntity<ApplicationResponseDTO> getApplicationById(@PathVariable UUID appId) {
        ApplicationResponseDTO application = applicationService.getApplicationById(appId);
        return ResponseEntity.ok(application);
    }
    
    @PostMapping
    @Operation(
            operationId = "createApplication",
            summary = "Create a new application",
            description = "Creates a new application in the system"
    )
    @ApiResponse(responseCode = "201", description = "Application created successfully")
    public ResponseEntity<ApplicationResponseDTO> createApplication(
            @Valid @RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO createdApplication = applicationService.createApplication(applicationRequestDTO);
        return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
    }
    
    @PutMapping("/{appId}")
    @Operation(
            operationId = "updateApplication",
            summary = "Update an application",
            description = "Updates an existing application by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Application updated successfully")
    @ApiResponse(responseCode = "404", description = "Application not found")
    public ResponseEntity<ApplicationResponseDTO> updateApplication(
            @PathVariable UUID appId,
            @Valid @RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO updatedApplication = applicationService.updateApplication(appId, applicationRequestDTO);
        return ResponseEntity.ok(updatedApplication);
    }
    
    @DeleteMapping("/{appId}")
    @Operation(
            operationId = "deleteApplication",
            summary = "Delete an application",
            description = "Deletes an application from the system by its ID"
    )
    @ApiResponse(responseCode = "204", description = "Application deleted successfully")
    @ApiResponse(responseCode = "404", description = "Application not found")
    public ResponseEntity<Void> deleteApplication(@PathVariable UUID appId) {
        applicationService.deleteApplication(appId);
        return ResponseEntity.noContent().build();
    }

}
