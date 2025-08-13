package vn.codegym.freelanceworkhub.service;

import vn.codegym.freelanceworkhub.model.Application;
import vn.codegym.freelanceworkhub.model.Job;
import java.util.List;
import vn.codegym.freelanceworkhub.model.User;

public interface IApplicationService {
    Application applyForJob(Long jobId, Long freelancerId);
    // Add other methods as needed, e.g., findByJobAndFreelancer, updateApplicationStatus
    List<Application> findByFreelancer(Long freelancerId);

    List<Application> findByFreelancerWithJobAndUser(Long freelancerId);

    List<Application> findAllByEmployerId(Long employerId);

    void acceptApplication(Long applicationId);
    void rejectApplication(Long applicationId);
}