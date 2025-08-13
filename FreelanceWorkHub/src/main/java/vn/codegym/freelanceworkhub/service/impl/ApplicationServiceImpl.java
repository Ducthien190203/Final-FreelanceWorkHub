package vn.codegym.freelanceworkhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.codegym.freelanceworkhub.dto.ApplicationDto;
import vn.codegym.freelanceworkhub.model.Application;
import vn.codegym.freelanceworkhub.model.Job;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.repository.ApplicationRepository;
import vn.codegym.freelanceworkhub.repository.JobRepository;
import vn.codegym.freelanceworkhub.repository.UserRepository;
import vn.codegym.freelanceworkhub.service.ApplicationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public Application apply(ApplicationDto dto, Long freelancerId) {
        Job job = jobRepository.findById(dto.getJobId())
                .orElseThrow(() -> new RuntimeException("Job không tồn tại"));
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer không tồn tại"));

        Application application = new Application();
        application.setJob(job);
        application.setFreelancer(freelancer);
        application.setCoverLetter(dto.getCoverLetter());
        application.setStatus(Application.ApplicationStatus.PENDING);

        return applicationRepository.save(application);
    }

    @Override
    public List<Application> findByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    @Override
    public List<Application> findByFreelancer(Long freelancerId) {
        return applicationRepository.findByFreelancerId(freelancerId);
    }

    @Override
    public Application findById(Long id) {
        return applicationRepository.findById(id).orElse(null);
    }

    @Override
    public void updateStatus(Long applicationId, String status) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application không tồn tại"));
        application.setStatus(Application.ApplicationStatus.valueOf(status.toUpperCase()));
        applicationRepository.save(application);
    }

    @Override
    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }
}
