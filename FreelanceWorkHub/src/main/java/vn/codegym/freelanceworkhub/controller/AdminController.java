package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.codegym.freelanceworkhub.service.IJobService;
import vn.codegym.freelanceworkhub.service.UserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final IJobService jobService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("totalJobs", jobService.findAll().size());
        return "admin/dashboard";
    }
}
