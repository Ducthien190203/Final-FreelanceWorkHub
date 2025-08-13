package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.codegym.freelanceworkhub.service.IJobService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final IJobService jobService;

    @GetMapping
    public String searchJobs(@RequestParam("keyword") String keyword, Model model) {
        // Tạm thời filter đơn giản, có thể viết query trong repository để tối ưu
        model.addAttribute("jobs", jobService.findAll().stream()
                .filter(j -> j.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList());
        model.addAttribute("keyword", keyword);
        return "search/results";
    }
}
