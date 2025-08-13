package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.codegym.freelanceworkhub.dto.EmployerProfileDto;
import vn.codegym.freelanceworkhub.model.EmployerProfile;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.service.EmployerProfileService;
import vn.codegym.freelanceworkhub.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import vn.codegym.freelanceworkhub.model.Job;
import vn.codegym.freelanceworkhub.model.Application;
import vn.codegym.freelanceworkhub.service.IJobService;
import vn.codegym.freelanceworkhub.service.IApplicationService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employer")
public class EmployerController {

    private final UserService userService;
    private final EmployerProfileService employerProfileService;
    private final IJobService jobService;
    private final IApplicationService applicationService;

    @GetMapping("/dashboard")
    public String employerDashboard() {
        return "dashboard-employer";
    }

    @GetMapping("/company-profile")
    public String showEmployerCompanyProfile(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Long userId = user.getId();

        EmployerProfile profile = employerProfileService.findByUserId(userId);
        if (profile == null) {
            profile = new EmployerProfile();
            profile.setUser(user);
        }
        model.addAttribute("profile", profile);
        model.addAttribute("user", user);
        return "employer-company-profile";
    }

    @PostMapping("/company-profile")
    public String updateEmployer(@Valid @ModelAttribute("profile") EmployerProfileDto dto,
                                 BindingResult result,
                                 @RequestParam("name") String name,
                                 Principal principal,
                                 Model model) {
        System.out.println("EmployerController: Entering updateEmployer method.");
        System.out.println("EmployerController: DTO received: " + dto);
        System.out.println("EmployerController: Name received: " + name);

        if (result.hasErrors()) {
            System.out.println("EmployerController: Validation errors found: " + result.getAllErrors());
            User user = userService.findByEmail(principal.getName());
            model.addAttribute("user", user);
            return "employer-company-profile";
        }
        System.out.println("EmployerController: No validation errors.");

        User user = userService.findByEmail(principal.getName());
        Long userId = user.getId();

        System.out.println("EmployerController: Updating user name to: " + name);
        user.setName(name);
        userService.save(user);
        System.out.println("EmployerController: User name updated.");

        System.out.println("EmployerController: Calling employerProfileService.createOrUpdate.");
        employerProfileService.createOrUpdate(userId, dto);
        System.out.println("EmployerController: employerProfileService.createOrUpdate completed.");

        return "redirect:/employer/company-profile";
    }

    @GetMapping("/job-list")
    public String listEmployerJobs(Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
                                                List<Job> employerJobs = jobService.findByEmployerWithApplications(user.getId());
        model.addAttribute("employerJobs", employerJobs);
        return "employer-job-list";
    }

    @GetMapping("/applicants")
    public String listApplicants(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getName());
        if (user == null) {
            return "redirect:/error";
        }

        List<Application> allApplications = applicationService.findAllByEmployerId(user.getId());
        model.addAttribute("allApplications", allApplications);
        return "employer-applicants";
    }

    @GetMapping("/applicant-detail/{id}")
    public String showApplicantDetail(@PathVariable("id") Long id, Model model) {
        User applicant = userService.findByIdWithProfile(id);
        if (applicant == null) {
            // Handle not found, maybe redirect to an error page or show a message
            return "redirect:/error";
        }
        model.addAttribute("applicant", applicant);
        return "applicant-detail";
    }

    @PostMapping("/applications/accept")
    public String acceptApplication(@RequestParam("applicationId") Long applicationId, RedirectAttributes redirectAttributes) {
        try {
            applicationService.acceptApplication(applicationId);
            redirectAttributes.addFlashAttribute("success", "Application accepted and job closed successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/employer/applicants";
    }

    @PostMapping("/applications/reject")
    public String rejectApplication(@RequestParam("applicationId") Long applicationId, RedirectAttributes redirectAttributes) {
        try {
            applicationService.rejectApplication(applicationId);
            redirectAttributes.addFlashAttribute("success", "Application rejected successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/employer/applicants";
    }
}