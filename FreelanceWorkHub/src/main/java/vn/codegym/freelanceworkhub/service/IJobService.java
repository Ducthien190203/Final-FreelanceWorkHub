package vn.codegym.freelanceworkhub.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import vn.codegym.freelanceworkhub.dto.CategoryStatsDTO;
import vn.codegym.freelanceworkhub.dto.JobDto;
import vn.codegym.freelanceworkhub.model.Job;

import java.util.List;

public interface IJobService {

    Job updateJob(JobDto dto);

    List<Job> findAll();

    Job findById(Long id);

    List<Job> findByCategory(String category);

    List<Job> findByEmployer(Long employerId);

    void deleteById(Long id);

    /**
     * Finds the newest jobs to display on the homepage.
     * @return A list of jobs.
     */
    List<Job> findNewestJobs();

    List<CategoryStatsDTO> getCategoryStats();

    // NEW METHOD FOR PAGINATED SEARCH
    Page<Job> findAll(Specification<Job> spec, Pageable pageable);

    Job save(Job job);

    void closeJob(Long jobId);

    List<Job> findByEmployerWithApplications(Long employerId);
}

