package com.bookingkols.webapp.controller;

import com.bookingkols.webapp.model.CategoryDTO;
import com.bookingkols.webapp.service.CategoryService;
import com.bookingkols.webapp.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/cms/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "cms/category/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("category") final CategoryDTO categoryDTO) {
        return "cms/category/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("category") @Valid final CategoryDTO categoryDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cms/category/add";
        }
        categoryService.create(categoryDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("category.create.success"));
        return "redirect:/cms/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("category", categoryService.get(id));
        return "cms/category/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("category") @Valid final CategoryDTO categoryDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cms/category/edit";
        }
        categoryService.update(id, categoryDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("category.update.success"));
        return "redirect:/cms/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        categoryService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("category.delete.success"));
        return "redirect:/cms/categories";
    }

}
