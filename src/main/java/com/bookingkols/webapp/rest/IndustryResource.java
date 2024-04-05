package com.bookingkols.webapp.rest;

import com.bookingkols.webapp.model.IndustryDTO;
import com.bookingkols.webapp.service.IndustryService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/industries", produces = MediaType.APPLICATION_JSON_VALUE)
public class IndustryResource {

    private final IndustryService industryService;

    public IndustryResource(final IndustryService industryService) {
        this.industryService = industryService;
    }

    @GetMapping
    public ResponseEntity<List<IndustryDTO>> getAllIndustries() {
        return ResponseEntity.ok(industryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndustryDTO> getIndustry(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(industryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createIndustry(@RequestBody @Valid final IndustryDTO industryDTO) {
        final Long createdId = industryService.create(industryDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateIndustry(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final IndustryDTO industryDTO) {
        industryService.update(id, industryDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteIndustry(@PathVariable(name = "id") final Long id) {
        industryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
