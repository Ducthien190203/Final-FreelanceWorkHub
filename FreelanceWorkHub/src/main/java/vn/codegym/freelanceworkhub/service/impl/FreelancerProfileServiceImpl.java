package vn.codegym.freelanceworkhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.codegym.freelanceworkhub.dto.FreelancerProfileDto;
import vn.codegym.freelanceworkhub.model.FreelancerProfile;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.repository.FreelancerProfileRepository;
import vn.codegym.freelanceworkhub.repository.UserRepository;
import vn.codegym.freelanceworkhub.service.FreelancerProfileService;

@Service
@RequiredArgsConstructor
@Transactional
public class FreelancerProfileServiceImpl implements FreelancerProfileService {

    private final FreelancerProfileRepository freelancerProfileRepository;
    private final UserRepository userRepository;

    @Override
    public FreelancerProfile createOrUpdate(Long userId, FreelancerProfileDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        FreelancerProfile profile = freelancerProfileRepository.findByUserId(userId)
                .orElse(new FreelancerProfile());
        profile.setUser(user);
        profile.setBio(dto.getBio());
        profile.setSkills(dto.getSkills());
        profile.setLocation(dto.getLocation());
        profile.setExperienceYears(dto.getExperienceYears());
        profile.setAvailability(dto.getAvailability());

        return freelancerProfileRepository.save(profile);
    }

    @Override
    public FreelancerProfile findByUserId(Long userId) {
        return freelancerProfileRepository.findByUserId(userId).orElse(null);
    }
}

