package vn.codegym.freelanceworkhub.service;

import vn.codegym.freelanceworkhub.dto.FreelancerProfileDto;
import vn.codegym.freelanceworkhub.model.FreelancerProfile;

public interface FreelancerProfileService {
    FreelancerProfile createOrUpdate(Long userId, FreelancerProfileDto dto);

    FreelancerProfile findByUserId(Long userId);
}
