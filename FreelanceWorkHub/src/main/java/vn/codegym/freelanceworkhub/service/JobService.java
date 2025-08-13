package vn.codegym.freelanceworkhub.service;

import vn.codegym.freelanceworkhub.dto.JobDto;
import vn.codegym.freelanceworkhub.model.Job;

import java.util.List;

public interface JobService {
    Job createJob(JobDto dto, Long employerId);

    Job updateJob(JobDto dto);

    List<Job> findAll();

    Job findById(Long id);

        List<Job> findByCategory(String categoryName);


    List<Job> findByEmployer(Long employerId);

    void deleteById(Long id);
}
