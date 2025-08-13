package vn.codegym.freelanceworkhub.service;

import vn.codegym.freelanceworkhub.dto.ApplicationDto;
import vn.codegym.freelanceworkhub.model.Application;

import java.util.List;

public interface ApplicationService {
    Application apply(ApplicationDto dto, Long freelancerId);

    List<Application> findByJob(Long jobId);

    List<Application> findByFreelancer(Long freelancerId);

    Application findById(Long id);

    void updateStatus(Long applicationId, String status);

    void deleteById(Long id);
}
