package vn.codegym.freelanceworkhub.service;

import vn.codegym.freelanceworkhub.dto.EmployerProfileDto;
import vn.codegym.freelanceworkhub.model.EmployerProfile;

public interface EmployerProfileService {
    EmployerProfile createOrUpdate(Long userId, EmployerProfileDto dto);

    EmployerProfile findByUserId(Long userId);
}
