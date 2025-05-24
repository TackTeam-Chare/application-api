package com.accellence.aps.service.impl;

import com.accellence.aps.dto.ApplicationRequestDTO;
import com.accellence.aps.dto.ApplicationResponseDTO;
import com.accellence.aps.entity.Application;
import com.accellence.aps.exception.ApplicationNotFoundException;
import com.accellence.aps.repository.ApplicationRepository;
import com.accellence.aps.service.ApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    
    private final ApplicationRepository applicationRepository;
    
    // Constructor injection
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    
    @Override
    public List<ApplicationResponseDTO> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ApplicationResponseDTO getApplicationById(UUID appId) {
        Application application = applicationRepository.findById(appId)
                .orElseThrow(() -> new ApplicationNotFoundException("Application not found with ID: " + appId));
        return mapToResponseDTO(application);
    }
    
    @Override
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO applicationRequestDTO) {
        Application application = mapToEntity(applicationRequestDTO);
        application.setCreatedOn(LocalDateTime.now());
        application.setModifiedOn(LocalDateTime.now());
        
        Application savedApplication = applicationRepository.save(application);
        return mapToResponseDTO(savedApplication);
    }
    
    @Override
    public ApplicationResponseDTO updateApplication(UUID appId, ApplicationRequestDTO applicationRequestDTO) {
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
        return mapToResponseDTO(updatedApplication);
    }
    
    @Override
    public void deleteApplication(UUID appId) {
        if (!applicationRepository.existsById(appId)) {
            throw new ApplicationNotFoundException("Application not found with ID: " + appId);
        }
        applicationRepository.deleteById(appId);
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
