package com.accellence.aps.service;

import com.accellence.aps.dto.ApplicationRequestDTO;
import com.accellence.aps.dto.ApplicationResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {
    
    List<ApplicationResponseDTO> getAllApplications();
    
    ApplicationResponseDTO getApplicationById(UUID appId);
    
    ApplicationResponseDTO createApplication(ApplicationRequestDTO applicationRequestDTO);
    
    ApplicationResponseDTO updateApplication(UUID appId, ApplicationRequestDTO applicationRequestDTO);
    
    void deleteApplication(UUID appId);
    
    void deleteAllApplications();
}
