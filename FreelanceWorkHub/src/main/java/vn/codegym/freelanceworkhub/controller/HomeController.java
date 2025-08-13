package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.codegym.freelanceworkhub.service.IJobService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final IJobService jobService;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        model.addAttribute("newestJobs", jobService.findNewestJobs()); // Changed key from "jobs" to "newestJobs"
        model.addAttribute("categoryStats", jobService.getCategoryStats()); // Added categoryStats
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}