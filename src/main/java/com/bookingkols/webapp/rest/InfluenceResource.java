package com.bookingkols.webapp.rest;

import com.bookingkols.webapp.model.InfluenceDTO;
import com.bookingkols.webapp.service.InfluenceService;
import com.bookingkols.webapp.util.ReferencedException;
import com.bookingkols.webapp.util.ReferencedWarning;
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
@RequestMapping(value = "/api/influences", produces = MediaType.APPLICATION_JSON_VALUE)
public class InfluenceResource {

    private final InfluenceService influenceService;

    public InfluenceResource(final InfluenceService influenceService) {
        this.influenceService = influenceService;
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

}
