package vn.codegym.freelanceworkhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.codegym.freelanceworkhub.model.JobCategory;
import vn.codegym.freelanceworkhub.repository.JobCategoryRepository;
import vn.codegym.freelanceworkhub.service.JobCategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class JobCategoryServiceImpl implements JobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;

    @Override
    public List<JobCategory> findAll() {
        return jobCategoryRepository.findAll();
    }

    @Override
    public JobCategory findById(Long id) {
        return jobCategoryRepository.findById(id).orElse(null);
    }

    @Override
    public JobCategory save(JobCategory category) {
        return jobCategoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        jobCategoryRepository.deleteById(id);
    }
}
