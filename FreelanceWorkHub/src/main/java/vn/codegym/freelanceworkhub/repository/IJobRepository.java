package vn.codegym.freelanceworkhub.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import vn.codegym.freelanceworkhub.dto.CategoryStatsDTO;
import vn.codegym.freelanceworkhub.model.Job;

import java.util.List;
import java.util.Optional;


@Repository
public interface IJobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    List<Job> findByCategory(String category);

    List<Job> findByEmployerId(Long employerId);

    /**
     * Finds the top 8 newest jobs by ordering by postedDate in descending order.
     * @return A list of the newest jobs.
     */
    List<Job> findTop8ByOrderByPostedDateDesc();

    @Query("SELECT NEW vn.codegym.freelanceworkhub.dto.CategoryStatsDTO(j.category, COUNT(j)) FROM Job j GROUP BY j.category ORDER BY COUNT(j) DESC")
    List<CategoryStatsDTO> countJobsInCategories();

    @Query("SELECT j FROM Job j LEFT JOIN FETCH j.applications WHERE j.employer.id = :employerId")
    List<Job> findByEmployerIdWithApplications(@Param("employerId") Long employerId);

    

    @Query("SELECT j FROM Job j JOIN FETCH j.employer WHERE j.id = :id")
    Optional<Job> findByIdWithEmployer(@Param("id") Long id);

    @Query("SELECT j FROM Job j LEFT JOIN FETCH j.applications WHERE j.id = :id")
    Optional<Job> findByIdWithApplications(@Param("id") Long id);

    @EntityGraph(attributePaths = "employer")
    Page<Job> findAll(Specification<Job> spec, Pageable pageable);

    @Query("SELECT j FROM Job j JOIN FETCH j.employer")
    List<Job> findAllWithEmployers();
}

    
    



