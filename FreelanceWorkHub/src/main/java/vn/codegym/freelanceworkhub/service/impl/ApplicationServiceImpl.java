package vn.codegym.freelanceworkhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.codegym.freelanceworkhub.model.Application;
import vn.codegym.freelanceworkhub.model.Job;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.repository.ApplicationRepository;
import vn.codegym.freelanceworkhub.repository.JobRepository;
import vn.codegym.freelanceworkhub.repository.UserRepository;
import vn.codegym.freelanceworkhub.service.IApplicationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements IApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public Application applyForJob(Long jobId, Long freelancerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer not found"));

        if (applicationRepository.findByJobAndFreelancer(job, freelancer).isPresent()) {
            throw new RuntimeException("You have already applied for this job.");
        }

        Application application = new Application();
        application.setJob(job);
        application.setFreelancer(freelancer);
        application.setStatus(Application.ApplicationStatus.PENDING);

        return applicationRepository.save(application);
    }

    @Override
    public List<Application> findByFreelancer(Long freelancerId) {
        return applicationRepository.findByFreelancerId(freelancerId);
    }

    @Override
    public List<Application> findByFreelancerWithJobAndUser(Long freelancerId) {
        return applicationRepository.findByFreelancerIdWithJobAndUser(freelancerId);
    }

    @Override
    public List<Application> findAllByEmployerId(Long employerId) {
        return applicationRepository.findAllByEmployerIdWithJobAndFreelancer(employerId);
    }
}