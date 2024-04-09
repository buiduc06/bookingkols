package com.bookingkols.webapp.controller;

import com.bookingkols.webapp.model.AdminDTO;
import com.bookingkols.webapp.model.Status;
import com.bookingkols.webapp.service.AdminService;
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
@RequestMapping("/cms/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(final AdminService adminService) {
        this.adminService = adminService;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("statusValues", Status.values());
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("admins", adminService.findAll());
        return "cms/admin/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("admin") final AdminDTO adminDTO) {
        return "cms/admin/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("admin") @Valid final AdminDTO adminDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cms/admin/add";
        }
        adminService.create(adminDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("admin.create.success"));
        return "redirect:/cms/admins";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("admin", adminService.get(id));
        return "cms/admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("admin") @Valid final AdminDTO adminDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cms/admin/edit";
        }
        adminService.update(id, adminDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("admin.update.success"));
        return "redirect:/cms/admins";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        adminService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("admin.delete.success"));
        return "redirect:/cms/admins";
    }

}
