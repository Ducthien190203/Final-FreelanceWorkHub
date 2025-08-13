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
import vn.codegym.freelanceworkhub.dto.EmployerProfileDto;
import vn.codegym.freelanceworkhub.dto.FreelancerProfileDto;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.model.FreelancerProfile;
import vn.codegym.freelanceworkhub.model.EmployerProfile;
import vn.codegym.freelanceworkhub.service.EmployerProfileService;
import vn.codegym.freelanceworkhub.service.FreelancerProfileService;
import vn.codegym.freelanceworkhub.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final FreelancerProfileService freelancerProfileService;
    private final EmployerProfileService employerProfileService;

    @GetMapping
    public String viewProfile(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Long userId = user.getId();
        model.addAttribute("user", user);
        if (user.getRole() == User.Role.FREELANCER) {
            FreelancerProfile profile = freelancerProfileService.findByUserId(userId);
            if (profile == null) {
                profile = new FreelancerProfile();
                profile.setUser(user);
            }
            model.addAttribute("profile", profile);
            return "freelancer-profile";
        } else { // Assuming EMPLOYER role
            EmployerProfile profile = employerProfileService.findByUserId(userId);
            if (profile == null) {
                profile = new EmployerProfile();
                profile.setUser(user);
            }
            model.addAttribute("profile", profile);
            return "employer-company-profile";
        }
    }

    @GetMapping("/edit")
    public String editProfile(Model model, Principal principal) {
        Long userId = Long.valueOf(principal.getName());
        User user = userService.findById(userId);
        if (user.getRole() == User.Role.FREELANCER) {
            model.addAttribute("profile", new FreelancerProfileDto());
            return "profile/freelancer-edit";
        }
        else {
            model.addAttribute("profile", new EmployerProfileDto());
            return "profile/employer-edit";
        }
    }

    @PostMapping("/edit/freelancer")
    public String updateFreelancer(@Valid @ModelAttribute("profile") FreelancerProfileDto dto,
                                   BindingResult result,
                                   @RequestParam("name") String name,
                                   Principal principal,
                                   Model model) {
        if (result.hasErrors()) {
            User user = userService.findByEmail(principal.getName());
            model.addAttribute("user", user);
            return "freelancer-profile";
        }
        User user = userService.findByEmail(principal.getName());
        Long userId = user.getId();

        user.setName(name);
        userService.save(user);

        freelancerProfileService.createOrUpdate(userId, dto);
        return "redirect:/profile";
    }
}