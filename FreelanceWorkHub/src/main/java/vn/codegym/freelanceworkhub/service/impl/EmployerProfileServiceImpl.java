package vn.codegym.freelanceworkhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.codegym.freelanceworkhub.dto.EmployerProfileDto;
import vn.codegym.freelanceworkhub.model.EmployerProfile;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.repository.EmployerProfileRepository;
import vn.codegym.freelanceworkhub.repository.UserRepository;
import vn.codegym.freelanceworkhub.service.EmployerProfileService;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployerProfileServiceImpl implements EmployerProfileService {

    private final EmployerProfileRepository employerProfileRepository;
    private final UserRepository userRepository;

    @Override
    public EmployerProfile createOrUpdate(Long userId, EmployerProfileDto dto) {
        System.out.println("EmployerProfileServiceImpl: createOrUpdate called for userId: " + userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        System.out.println("EmployerProfileServiceImpl: User found: " + user.getEmail());

        EmployerProfile profile = employerProfileRepository.findByUserId(userId)
                .orElse(new EmployerProfile());
        System.out.println("EmployerProfileServiceImpl: Profile found/created. ID: " + profile.getId() + ", CompanyName: " + profile.getCompanyName());

        profile.setUser(user);
        profile.setCompanyName(user.getName());
        profile.setCompanyDescription(dto.getCompanyDescription());
        profile.setWebsite(dto.getWebsite());
        profile.setLocation(dto.getLocation());

        System.out.println("EmployerProfileServiceImpl: Attempting to save profile with new data - CompanyName: " + dto.getCompanyName() + ", Location: " + dto.getLocation());
        EmployerProfile savedProfile = employerProfileRepository.save(profile);
        System.out.println("EmployerProfileServiceImpl: Profile saved successfully. ID: " + savedProfile.getId());

        return savedProfile;
    }

    @Override
    public EmployerProfile findByUserId(Long userId) {
        return employerProfileRepository.findByUserId(userId).orElse(null);
    }
}
