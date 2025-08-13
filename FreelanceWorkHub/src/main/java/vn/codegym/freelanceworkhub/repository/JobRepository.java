package vn.codegym.freelanceworkhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codegym.freelanceworkhub.model.Job;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
        List<Job> findByCategory(String categoryName);


    List<Job> findByEmployerId(Long employerId);
}
