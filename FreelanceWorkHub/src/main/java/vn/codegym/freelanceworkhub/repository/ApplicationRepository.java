package vn.codegym.freelanceworkhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codegym.freelanceworkhub.model.Application;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByJobId(Long jobId);

    List<Application> findByFreelancerId(Long freelancerId);

    Optional<Application> findByJobIdAndFreelancerId(Long jobId, Long freelancerId);
}
