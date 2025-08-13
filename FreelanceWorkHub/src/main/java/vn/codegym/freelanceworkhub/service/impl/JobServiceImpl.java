package vn.codegym.freelanceworkhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.codegym.freelanceworkhub.dto.CategoryStatsDTO;
import vn.codegym.freelanceworkhub.dto.JobDto;
import vn.codegym.freelanceworkhub.model.Job;
import vn.codegym.freelanceworkhub.model.JobCategory;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.repository.IJobRepository;
import vn.codegym.freelanceworkhub.repository.JobCategoryRepository;
import vn.codegym.freelanceworkhub.repository.UserRepository;
import vn.codegym.freelanceworkhub.service.IJobService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JobServiceImpl implements IJobService {

    private final IJobRepository jobRepository;
    private final JobCategoryRepository jobCategoryRepository;
    private final UserRepository userRepository;

    

    @Override
    public Job updateJob(JobDto dto) {
        Job job = jobRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Job không tồn tại"));
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setBudget(dto.getBudget());
        JobCategory category = jobCategoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Danh mục không tồn tại"));
        job.setCategory(category.getName()); // Set the category name (String) instead of the object
        job.setStatus(Job.JobStatus.valueOf(dto.getStatus().toUpperCase()));
        return jobRepository.save(job);
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job findById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public List<Job> findByCategory(String category) {
        return jobRepository.findByCategory(category);
    }

    @Override
    public List<Job> findByEmployer(Long employerId) {
        return jobRepository.findByEmployerId(employerId);
    }

    @Override
    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }

    @Override
    public List<Job> findNewestJobs() {
        return jobRepository.findTop8ByOrderByPostedDateDesc();
    }

    @Override
    public List<CategoryStatsDTO> getCategoryStats() {
        return jobRepository.countJobsInCategories();
    }

    // NEW METHOD FOR PAGINATED SEARCH
    @Override
    public Page<Job> findAll(Specification<Job> spec, Pageable pageable) {
        return jobRepository.findAll(spec, pageable);
    }

    @Override
    public Job save(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void closeJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));
        job.setStatus(Job.JobStatus.CLOSED);
        jobRepository.save(job);
    }

    
}