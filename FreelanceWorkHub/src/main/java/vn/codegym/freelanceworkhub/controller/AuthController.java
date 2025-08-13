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
import vn.codegym.freelanceworkhub.dto.UserRegistrationDto;
import vn.codegym.freelanceworkhub.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * Hiển thị trang chọn vai trò (Client hoặc Freelancer).
     * Đây là điểm bắt đầu của quá trình đăng ký.
     */
    @GetMapping("/register")
    public String showRoleSelectionPage() {
        return "register-role-select";
    }

    /**
     * Hiển thị form đăng ký chi tiết sau khi người dùng đã chọn vai trò.
     */
    @GetMapping("/register/form")
    public String showRegistrationForm(@RequestParam("role") String role, Model model) {
        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setRole(role);
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegistrationDto dto,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        try {
            userService.register(dto);
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}