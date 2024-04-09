package com.bookingkols.webapp.controller;

import com.bookingkols.webapp.domain.Category;
import com.bookingkols.webapp.domain.Industry;
import com.bookingkols.webapp.domain.Platform;
import com.bookingkols.webapp.model.Gender;
import com.bookingkols.webapp.model.InfluenceDTO;
import com.bookingkols.webapp.model.Status;
import com.bookingkols.webapp.repos.CategoryRepository;
import com.bookingkols.webapp.repos.IndustryRepository;
import com.bookingkols.webapp.repos.PlatformRepository;
import com.bookingkols.webapp.service.InfluenceService;
import com.bookingkols.webapp.util.CustomCollectors;
import com.bookingkols.webapp.util.ReferencedWarning;
import com.bookingkols.webapp.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
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
@RequestMapping("/cms/influences")
public class InfluenceController {

    private final InfluenceService influenceService;
    private final PlatformRepository platformRepository;
    private final CategoryRepository categoryRepository;
    private final IndustryRepository industryRepository;

    public InfluenceController(final InfluenceService influenceService,
            final PlatformRepository platformRepository,
            final CategoryRepository categoryRepository,
            final IndustryRepository industryRepository) {
        this.influenceService = influenceService;
        this.platformRepository = platformRepository;
        this.categoryRepository = categoryRepository;
        this.industryRepository = industryRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("genderValues", Gender.values());
        model.addAttribute("statusValues", Status.values());
        model.addAttribute("platformsValues", platformRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Platform::getId, Platform::getName)));
        model.addAttribute("categoriesValues", categoryRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Category::getId, Category::getName)));
        model.addAttribute("industriesValues", industryRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Industry::getId, Industry::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("influences", influenceService.findAll());
        return "cms/influence/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("influence") final InfluenceDTO influenceDTO) {
        return "cms/influence/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("influence") @Valid final InfluenceDTO influenceDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cms/influence/add";
        }
        influenceService.create(influenceDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("influence.create.success"));
        return "redirect:/cms/influences";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("influence", influenceService.get(id));
        return "cms/influence/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("influence") @Valid final InfluenceDTO influenceDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cms/influence/edit";
        }
        influenceService.update(id, influenceDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("influence.update.success"));
        return "redirect:/cms/influences";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = influenceService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            influenceService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("influence.delete.success"));
        }
        return "redirect:/cms/influences";
    }

}
