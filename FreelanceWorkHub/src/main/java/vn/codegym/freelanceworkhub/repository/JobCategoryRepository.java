package vn.codegym.freelanceworkhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codegym.freelanceworkhub.model.JobCategory;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
}
