package com.bookingkols.webapp.controller;

import com.bookingkols.webapp.model.PlatformDTO;
import com.bookingkols.webapp.service.PlatformService;
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
@RequestMapping("/cms/platforms")
public class PlatformController {

    private final PlatformService platformService;

    public PlatformController(final PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("platforms", platformService.findAll());
        return "cms/platform/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("platform") final PlatformDTO platformDTO) {
        return "cms/platform/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("platform") @Valid final PlatformDTO platformDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cms/platform/add";
        }
        platformService.create(platformDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("platform.create.success"));
        return "redirect:/cms/platforms";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("platform", platformService.get(id));
        return "cms/platform/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("platform") @Valid final PlatformDTO platformDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cms/platform/edit";
        }
        platformService.update(id, platformDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("platform.update.success"));
        return "redirect:/cms/platforms";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        platformService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("platform.delete.success"));
        return "redirect:/cms/platforms";
    }

}
