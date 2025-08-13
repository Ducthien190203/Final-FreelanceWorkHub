package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.codegym.freelanceworkhub.model.Job;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.service.IJobService;
import vn.codegym.freelanceworkhub.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final IJobService jobService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<User> users = userService.findAll();
        List<Job> jobs = jobService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("jobs", jobs);
        model.addAttribute("totalUsers", users.size());
        model.addAttribute("totalJobs", jobs.size());
        return "dashboard-admin";
    }

    @PostMapping("/users/ban/{id}")
    public String banUser(@PathVariable Long id) {
        userService.banUser(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/users/unban/{id}")
    public String unbanUser(@PathVariable Long id) {
        userService.unbanUser(id);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/jobs/close/{id}")
    public String closeJob(@PathVariable Long id) {
        jobService.closeJob(id);
        return "redirect:/admin/dashboard";
    }
}
