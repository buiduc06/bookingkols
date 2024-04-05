package com.bookingkols.webapp.controller;

import com.bookingkols.webapp.model.IndustryDTO;
import com.bookingkols.webapp.service.IndustryService;
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
@RequestMapping("/industries")
public class IndustryController {

    private final IndustryService industryService;

    public IndustryController(final IndustryService industryService) {
        this.industryService = industryService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("industries", industryService.findAll());
        return "industry/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("industry") final IndustryDTO industryDTO) {
        return "industry/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("industry") @Valid final IndustryDTO industryDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "industry/add";
        }
        industryService.create(industryDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("industry.create.success"));
        return "redirect:/industries";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("industry", industryService.get(id));
        return "industry/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("industry") @Valid final IndustryDTO industryDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "industry/edit";
        }
        industryService.update(id, industryDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("industry.update.success"));
        return "redirect:/industries";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        industryService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("industry.delete.success"));
        return "redirect:/industries";
    }

}
