package com.accellence.aps.service.impl;

import com.accellence.aps.dto.ApplicationRequestDTO;
import com.accellence.aps.dto.ApplicationResponseDTO;
import com.accellence.aps.entity.Application;
import com.accellence.aps.exception.ApplicationNotFoundException;
import com.accellence.aps.repository.ApplicationRepository;
import com.accellence.aps.service.ApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {
    
    private final ApplicationRepository applicationRepository;
    
    // Constructor injection
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    
    @Override
    public List<ApplicationResponseDTO> getAllApplications() {
        log.info("Retrieving all applications");
        List<Application> applications = applicationRepository.findAll();
        return applications.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ApplicationResponseDTO getApplicationById(UUID appId) {
        log.info("Retrieving application with ID: {}", appId);
        Application application = applicationRepository.findById(appId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with ID: " + appId));
        return mapToResponseDTO(application);
    }
    
    @Override
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO applicationRequestDTO) {
        log.info("Creating new application with product type: {}, product program: {}", 
                applicationRequestDTO.getProductType(), applicationRequestDTO.getProductProgram());
        
        Application application = mapToEntity(applicationRequestDTO);
        application.setCreatedOn(LocalDateTime.now());
        application.setModifiedOn(LocalDateTime.now());
        
        Application savedApplication = applicationRepository.save(application);
        log.info("Successfully created application with ID: {}", savedApplication.getAppId());
        return mapToResponseDTO(savedApplication);
    }
    
    @Override
    public ApplicationResponseDTO updateApplication(UUID appId, ApplicationRequestDTO applicationRequestDTO) {
        log.info("Updating application with ID: {}", appId);
        
        Application existingApplication = applicationRepository.findById(appId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with ID: " + appId));
        
        // Update fields
        existingApplication.setProductType(applicationRequestDTO.getProductType());
        existingApplication.setProductProgram(applicationRequestDTO.getProductProgram());
        existingApplication.setCardType(applicationRequestDTO.getCardType());
        existingApplication.setCampaignCode(applicationRequestDTO.getCampaignCode());
        existingApplication.setVip(applicationRequestDTO.isVip());
        existingApplication.setAppStatus(applicationRequestDTO.getAppStatus());
        existingApplication.setModifiedOn(LocalDateTime.now());
        
        Application updatedApplication = applicationRepository.save(existingApplication);
        log.info("Successfully updated application with ID: {}", updatedApplication.getAppId());
        return mapToResponseDTO(updatedApplication);
    }
    
    @Override
    public void deleteApplication(UUID appId) {
        log.info("Deleting application with ID: {}", appId);
        
        if (!applicationRepository.existsById(appId)) {
            log.warn("Failed to delete application: ID {} not found", appId);
            throw new ApplicationNotFoundException("Application not found with ID: " + appId);
        }
        
        applicationRepository.deleteById(appId);
        log.info("Successfully deleted application with ID: {}", appId);
    }
    
    @Override
    @org.springframework.transaction.annotation.Transactional
    public void deleteAllApplications() {
        log.info("Deleting ALL applications - this operation cannot be undone");
        
        long count = applicationRepository.count();
        applicationRepository.deleteAll();
        
        log.info("Successfully deleted all applications. Total records deleted: {}", count);
    }

    // Helper methods for mapping between entities and DTOs
    private ApplicationResponseDTO mapToResponseDTO(Application application) {
        ApplicationResponseDTO responseDTO = new ApplicationResponseDTO();
        responseDTO.setAppId(application.getAppId());
        responseDTO.setProductType(application.getProductType());
        responseDTO.setProductProgram(application.getProductProgram());
        responseDTO.setCardType(application.getCardType());
        responseDTO.setCampaignCode(application.getCampaignCode());
        responseDTO.setVip(application.isVip());
        responseDTO.setAppStatus(application.getAppStatus());
        responseDTO.setCreatedOn(application.getCreatedOn());
        responseDTO.setModifiedOn(application.getModifiedOn());
        return responseDTO;
    }
    
    private Application mapToEntity(ApplicationRequestDTO requestDTO) {
        Application application = new Application();
        application.setProductType(requestDTO.getProductType());
        application.setProductProgram(requestDTO.getProductProgram());
        application.setCardType(requestDTO.getCardType());
        application.setCampaignCode(requestDTO.getCampaignCode());
        application.setVip(requestDTO.isVip());
        application.setAppStatus(requestDTO.getAppStatus());
        return application;
    }
}
