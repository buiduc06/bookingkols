package com.bookingkols.webapp.controller;

import com.bookingkols.webapp.domain.Influence;
import com.bookingkols.webapp.model.VideoDTO;
import com.bookingkols.webapp.repos.InfluenceRepository;
import com.bookingkols.webapp.service.VideoService;
import com.bookingkols.webapp.util.CustomCollectors;
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
@RequestMapping("/cms/videos")
public class VideoController {

    private final VideoService videoService;
    private final InfluenceRepository influenceRepository;

    public VideoController(final VideoService videoService,
            final InfluenceRepository influenceRepository) {
        this.videoService = videoService;
        this.influenceRepository = influenceRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("influenceValues", influenceRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Influence::getId, Influence::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("videos", videoService.findAll());
        return "cms/video/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("video") final VideoDTO videoDTO) {
        return "cms/video/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("video") @Valid final VideoDTO videoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cms/video/add";
        }
        videoService.create(videoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("video.create.success"));
        return "redirect:/cms/videos";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("video", videoService.get(id));
        return "cms/video/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("video") @Valid final VideoDTO videoDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "cms/video/edit";
        }
        videoService.update(id, videoDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("video.update.success"));
        return "redirect:/cms/videos";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        videoService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("video.delete.success"));
        return "redirect:/cms/videos";
    }

}
