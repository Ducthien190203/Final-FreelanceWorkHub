package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.codegym.freelanceworkhub.dto.JobDto;
import vn.codegym.freelanceworkhub.model.Job;
import vn.codegym.freelanceworkhub.model.JobCategory;
import vn.codegym.freelanceworkhub.service.JobCategoryService;
import vn.codegym.freelanceworkhub.service.IJobService;
import vn.codegym.freelanceworkhub.service.specification.JobSpecification;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.Optional;

import vn.codegym.freelanceworkhub.service.UserService;
import vn.codegym.freelanceworkhub.model.User;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobController {

    private final IJobService jobService;
    private final JobCategoryService jobCategoryService;
    private final UserService userService;

    @GetMapping
    public String listJobs(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate,desc") String sort,
            Model model) {

        System.out.println("JobController.listJobs - Received parameters:");
        System.out.println("  Keyword: " + keyword);
        System.out.println("  Category: " + category);
        System.out.println("  Status: " + status);
        System.out.println("  Page: " + page);
        System.out.println("  Size: " + size);
        System.out.println("  Sort: " + sort);

        // Parse sort parameter
        Sort sortOrder = Sort.by(Sort.Direction.fromString(sort.split(",")[1]), sort.split(",")[0]);
        
        // Add custom sort to push CLOSED jobs to the end
        sortOrder = sortOrder.and(Sort.by("status").ascending()); // PENDING, APPROVED, CLOSED

        // 1. Tạo Pageable
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // 2. Xây dựng Specification
        Specification<Job> spec = Specification.where(JobSpecification.hasKeyword(keyword))
                                                .and(JobSpecification.hasStatus(status));
        if (category != null && !category.trim().isEmpty()) {
            spec = spec.and(JobSpecification.hasCategory(category));
        }

        // 3. Gọi service
        Page<Job> jobPage = jobService.findAll(spec, pageable);

        System.out.println("JobController.listJobs - Search results:");
        System.out.println("  Total elements: " + jobPage.getTotalElements());
        System.out.println("  Number of jobs on current page: " + jobPage.getContent().size());

        // 4. Đưa dữ liệu vào Model
        model.addAttribute("jobPage", jobPage);
        model.addAttribute("jobs", jobPage.getContent()); // For existing th:each="job : ${jobs}"
        model.addAttribute("categories", jobCategoryService.findAll()); // Needed for filter form

        // Đưa các tham số lọc vào lại model để giữ trạng thái trên form
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("status", status);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);

        return "jobs";
    }

    @GetMapping("/{id}")
    public String jobDetail(@PathVariable Long id, Model model) {
        Job job = jobService.findById(id);
        if (job == null) {
            return "redirect:/jobs?notfound";
        }
        model.addAttribute("job", job);
        return "jobs/detail";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("job", new JobDto());
        model.addAttribute("categories", jobCategoryService.findAll());
        return "jobs/create";
    }

    @PostMapping("/create")
    public String processPostJobForm(@Valid @ModelAttribute("job") JobDto jobDto,
                                     BindingResult result,
                                     Principal principal,
                                     Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", jobCategoryService.findAll());
            return "jobs/create";
        }

        JobCategory category = jobCategoryService.findById(jobDto.getCategoryId());
        if (category == null) {
            result.rejectValue("categoryId", "error.job", "Invalid category selected.");
            model.addAttribute("categories", jobCategoryService.findAll());
            return "jobs/create";
        }

        Job job = new Job();
        job.setTitle(jobDto.getTitle());
        job.setDescription(jobDto.getDescription());
        job.setResponsibilities(jobDto.getResponsibilities());
        job.setRequirements(jobDto.getRequirements());
        job.setSkills(jobDto.getSkills());
        job.setExperienceLevel(jobDto.getExperienceLevel());
        job.setLocationType(jobDto.getLocationType());
        job.setLocationAddress(jobDto.getOnSiteAddress());
        job.setPaymentType(jobDto.getPaymentType());
        job.setMinRate(jobDto.getMinBudget());
        job.setMaxRate(jobDto.getMaxBudget());
        job.setCategory(category.getName());

        User currentEmployer = userService.findByEmail(principal.getName());
        if (currentEmployer == null) {
            // Handle the case where the employer is not found (e.g., redirect to login or error page)
            // For now, let's add an error to the model and return to the form
            model.addAttribute("error", "Employer not found. Please log in again.");
            model.addAttribute("categories", jobCategoryService.findAll());
            return "jobs/create";
        }
        job.setEmployer(currentEmployer);
        job.setPostedDate(new Date()); // Explicitly set postedDate

        jobService.save(job);

        return "redirect:/employer/job-list";
    }

    @PostMapping("/employer/jobs/close/{id}")
    public String closeJob(@PathVariable Long id) {
        jobService.closeJob(id);
        return "redirect:/employer/job-list";
    }
}