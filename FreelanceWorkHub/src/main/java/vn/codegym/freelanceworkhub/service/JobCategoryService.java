package vn.codegym.freelanceworkhub.service;

import vn.codegym.freelanceworkhub.model.JobCategory;

import java.util.List;

public interface JobCategoryService {
    List<JobCategory> findAll();

    JobCategory findById(Long id);

    JobCategory save(JobCategory category);

    void deleteById(Long id);
}
