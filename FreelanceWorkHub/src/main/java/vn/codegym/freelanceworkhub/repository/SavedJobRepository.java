package vn.codegym.freelanceworkhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codegym.freelanceworkhub.model.SavedJob;

import java.util.List;
import java.util.Optional;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findByFreelancerId(Long freelancerId);

    Optional<SavedJob> findByJobIdAndFreelancerId(Long jobId, Long freelancerId);
}
