package vn.codegym.freelanceworkhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.codegym.freelanceworkhub.model.Job;
import vn.codegym.freelanceworkhub.model.SavedJob;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.repository.JobRepository;
import vn.codegym.freelanceworkhub.repository.SavedJobRepository;
import vn.codegym.freelanceworkhub.repository.UserRepository;
import vn.codegym.freelanceworkhub.service.SavedJobService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SavedJobServiceImpl implements SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public SavedJob saveJob(Long jobId, Long freelancerId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job không tồn tại"));
        User freelancer = userRepository.findById(freelancerId)
                .orElseThrow(() -> new RuntimeException("Freelancer không tồn tại"));

        if (savedJobRepository.findByJobIdAndFreelancerId(jobId, freelancerId).isPresent()) {
            throw new RuntimeException("Job đã được lưu trước đó");
        }

        SavedJob savedJob = new SavedJob();
        savedJob.setJob(job);
        savedJob.setFreelancer(freelancer);

        return savedJobRepository.save(savedJob);
    }

    @Override
    public void removeSavedJob(Long jobId, Long freelancerId) {
        savedJobRepository.findByJobIdAndFreelancerId(jobId, freelancerId)
                .ifPresent(savedJobRepository::delete);
    }

    @Override
    public List<SavedJob> findByFreelancer(Long freelancerId) {
        return savedJobRepository.findByFreelancerId(freelancerId);
    }
}
