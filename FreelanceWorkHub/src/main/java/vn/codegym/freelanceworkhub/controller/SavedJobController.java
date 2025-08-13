package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.service.SavedJobService;
import vn.codegym.freelanceworkhub.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/saved-jobs")
public class SavedJobController {

    private final SavedJobService savedJobService;
    private final UserService userService;

    @GetMapping
    public String listSavedJobs(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Long freelancerId = user.getId();
        model.addAttribute("savedJobs", savedJobService.findByFreelancer(freelancerId));
                    return "freelancer-saved-jobs";
    }

    @PostMapping("/add/{jobId}")
    public String saveJob(@PathVariable Long jobId, Principal principal) {
        Long freelancerId = Long.valueOf(principal.getName());
        savedJobService.saveJob(jobId, freelancerId);
        return "redirect:/jobs/" + jobId;
    }

    @GetMapping("/remove/{jobId}")
    public String removeSavedJob(@PathVariable Long jobId, Principal principal) {
        Long freelancerId = Long.valueOf(principal.getName());
        savedJobService.removeSavedJob(jobId, freelancerId);
        return "redirect:/saved-jobs";
    }
}
