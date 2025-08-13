package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.service.IApplicationService;
import vn.codegym.freelanceworkhub.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final IApplicationService applicationService;
    private final UserService userService;

    @PostMapping("/apply")
    public String applyForJob(@RequestParam("jobId") Long jobId,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to apply for a job.");
            return "redirect:/login";
        }

        User currentUser = userService.findByEmail(principal.getName());
        if (currentUser == null || !currentUser.getRole().name().equals("FREELANCER")) {
            redirectAttributes.addFlashAttribute("error", "Only freelancers can apply for jobs.");
            return "redirect:/jobs/" + jobId; // Redirect back to job detail
        }

        try {
            applicationService.applyForJob(jobId, currentUser.getId());
            redirectAttributes.addFlashAttribute("success", "Application submitted successfully!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/jobs/" + jobId; // Redirect back to job detail
    }
}