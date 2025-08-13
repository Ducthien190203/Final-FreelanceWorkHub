package vn.codegym.freelanceworkhub.service.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.codegym.freelanceworkhub.model.Job;
import vn.codegym.freelanceworkhub.model.JobCategory;
import vn.codegym.freelanceworkhub.model.EmployerProfile; // Keep for potential future use or reference
import vn.codegym.freelanceworkhub.model.User; // Keep for potential future use or reference

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class JobSpecification {

    public static Specification<Job> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String likePattern = "%" + keyword.toLowerCase().trim() + "%";
            return criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
            );
        };
    }

    public static Specification<Job> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null || category.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(criteriaBuilder.lower(root.get("category")), category.toLowerCase());
        };
    }

    public static Specification<Job> hasStatus(String status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null || status.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            try {
                Job.JobStatus jobStatus = Job.JobStatus.valueOf(status.toUpperCase());
                return criteriaBuilder.equal(root.get("status"), jobStatus);
            } catch (IllegalArgumentException e) {
                // Handle invalid status string, e.g., return a predicate that matches nothing
                return criteriaBuilder.disjunction(); // Always false predicate
            }
        };
    }

    

    public static Specification<Job> isNotClosed() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.notEqual(root.get("status"), Job.JobStatus.CLOSED);
        };
    }

    public static Specification<Job> isPending() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), Job.JobStatus.PENDING);
        };
    }
}