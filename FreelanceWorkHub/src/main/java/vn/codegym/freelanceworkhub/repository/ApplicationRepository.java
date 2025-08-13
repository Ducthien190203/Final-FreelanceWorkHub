package vn.codegym.freelanceworkhub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.codegym.freelanceworkhub.model.Application;
import vn.codegym.freelanceworkhub.model.Job;
import vn.codegym.freelanceworkhub.model.User;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByJobId(Long jobId);

    List<Application> findByFreelancerId(Long freelancerId);

    Optional<Application> findByJobIdAndFreelancerId(Long jobId, Long freelancerId);
    Optional<Application> findByJobAndFreelancer(Job job, User freelancer);

    @Query("SELECT a FROM Application a JOIN FETCH a.job j JOIN FETCH a.freelancer WHERE a.freelancer.id = :freelancerId")
    List<Application> findByFreelancerIdWithJobAndUser(@Param("freelancerId") Long freelancerId);

    @Query("SELECT a FROM Application a JOIN FETCH a.job j JOIN FETCH a.freelancer f WHERE j.employer.id = :employerId")
    List<Application> findAllByEmployerIdWithJobAndFreelancer(@Param("employerId") Long employerId);

    List<Application> findByJobAndStatusAndIdNot(Job job, Application.ApplicationStatus status, Long id);
}
