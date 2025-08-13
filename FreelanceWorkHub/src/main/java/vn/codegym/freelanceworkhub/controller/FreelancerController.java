package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.service.ApplicationService;
import vn.codegym.freelanceworkhub.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/freelancer")
public class FreelancerController {

    private final ApplicationService applicationService;
    private final UserService userService;

    @GetMapping("/dashboard")
    public String freelancerDashboard() {
        return "dashboard-freelancer";
    }

    @GetMapping("/applications")
    public String listFreelancerApplications(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Long freelancerId = user.getId();
        model.addAttribute("applications", applicationService.findByFreelancer(freelancerId));
        return "freelancer-applications";
    }
}