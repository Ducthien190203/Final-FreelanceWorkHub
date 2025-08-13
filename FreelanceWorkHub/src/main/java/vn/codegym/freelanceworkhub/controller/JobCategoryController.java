package vn.codegym.freelanceworkhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.codegym.freelanceworkhub.model.JobCategory;
import vn.codegym.freelanceworkhub.service.JobCategoryService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class JobCategoryController {

    private final JobCategoryService jobCategoryService;

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", jobCategoryService.findAll());
        return "categories/list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("category", new JobCategory());
        return "categories/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("category") JobCategory category,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "categories/create";
        }
        jobCategoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", jobCategoryService.findById(id));
        return "categories/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("category") JobCategory category,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "categories/edit";
        }
        jobCategoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        jobCategoryService.deleteById(id);
        return "redirect:/categories";
    }
}
