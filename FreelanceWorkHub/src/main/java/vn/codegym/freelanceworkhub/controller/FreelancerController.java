package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.service.IApplicationService;
import vn.codegym.freelanceworkhub.service.UserService;

import java.security.Principal;
import java.util.List;
import vn.codegym.freelanceworkhub.model.Application;

@Controller
@RequiredArgsConstructor
@RequestMapping("/freelancer")
public class FreelancerController {

    private final IApplicationService applicationService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String freelancerDashboard() {
        return "dashboard-freelancer";
    }

    @GetMapping("/applications")
    public String listFreelancerApplications(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getName());
        if (user == null) {
            return "redirect:/error"; 
        }

        Long freelancerId = user.getId();
        List<Application> applications = applicationService.findByFreelancerWithJobAndUser(freelancerId);
        model.addAttribute("applications", applications);
        return "freelancer-applications";
    }
}