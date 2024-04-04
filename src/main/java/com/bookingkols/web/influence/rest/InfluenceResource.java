package com.bookingkols.web.influence.rest;

import com.bookingkols.web.category.domain.Category;
import com.bookingkols.web.category.repos.CategoryRepository;
import com.bookingkols.web.industry.domain.Industry;
import com.bookingkols.web.industry.repos.IndustryRepository;
import com.bookingkols.web.influence.model.InfluenceDTO;
import com.bookingkols.web.influence.service.InfluenceService;
import com.bookingkols.web.platform.domain.Platform;
import com.bookingkols.web.platform.repos.PlatformRepository;
import com.bookingkols.web.util.CustomCollectors;
import com.bookingkols.web.util.ReferencedException;
import com.bookingkols.web.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/influences", produces = MediaType.APPLICATION_JSON_VALUE)
public class InfluenceResource {

    private final InfluenceService influenceService;
    private final PlatformRepository platformRepository;
    private final CategoryRepository categoryRepository;
    private final IndustryRepository industryRepository;

    public InfluenceResource(final InfluenceService influenceService,
            final PlatformRepository platformRepository,
            final CategoryRepository categoryRepository,
            final IndustryRepository industryRepository) {
        this.influenceService = influenceService;
        this.platformRepository = platformRepository;
        this.categoryRepository = categoryRepository;
        this.industryRepository = industryRepository;
    }

    @GetMapping
    public ResponseEntity<List<InfluenceDTO>> getAllInfluences() {
        return ResponseEntity.ok(influenceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfluenceDTO> getInfluence(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(influenceService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createInfluence(
            @RequestBody @Valid final InfluenceDTO influenceDTO) {
        final Long createdId = influenceService.create(influenceDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateInfluence(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final InfluenceDTO influenceDTO) {
        influenceService.update(id, influenceDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteInfluence(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = influenceService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        influenceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/platformsValues")
    public ResponseEntity<Map<Long, String>> getPlatformsValues() {
        return ResponseEntity.ok(platformRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Platform::getId, Platform::getName)));
    }

    @GetMapping("/categoriesValues")
    public ResponseEntity<Map<Long, String>> getCategoriesValues() {
        return ResponseEntity.ok(categoryRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Category::getId, Category::getName)));
    }

    @GetMapping("/industriesValues")
    public ResponseEntity<Map<Long, String>> getIndustriesValues() {
        return ResponseEntity.ok(industryRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Industry::getId, Industry::getName)));
    }

}
