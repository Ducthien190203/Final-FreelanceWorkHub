package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.codegym.freelanceworkhub.dto.ApplicationDto;
import vn.codegym.freelanceworkhub.service.ApplicationService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/job/{jobId}")
    public String listApplications(@PathVariable Long jobId, Model model) {
        model.addAttribute("applications", applicationService.findByJob(jobId));
        return "applications/list";
    }

    @GetMapping("/apply/{jobId}")
    public String applyForm(@PathVariable Long jobId, Model model) {
        ApplicationDto dto = new ApplicationDto();
        dto.setJobId(jobId);
        model.addAttribute("application", dto);
        return "applications/apply";
    }

    @PostMapping("/apply")
    public String apply(@Valid @ModelAttribute("application") ApplicationDto dto,
                        BindingResult result,
                        Principal principal) {
        if (result.hasErrors()) {
            return "applications/apply";
        }
        Long freelancerId = Long.valueOf(principal.getName());
        applicationService.apply(dto, freelancerId);
        return "redirect:/jobs/" + dto.getJobId();
    }
}
