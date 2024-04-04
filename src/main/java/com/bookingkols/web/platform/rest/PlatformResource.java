package com.bookingkols.web.platform.rest;

import com.bookingkols.web.platform.model.PlatformDTO;
import com.bookingkols.web.platform.service.PlatformService;
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
@RequestMapping(value = "/api/platforms", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlatformResource {

    private final PlatformService platformService;

    public PlatformResource(final PlatformService platformService) {
        this.platformService = platformService;
    }

    @GetMapping
    public ResponseEntity<List<PlatformDTO>> getAllPlatforms() {
        return ResponseEntity.ok(platformService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatformDTO> getPlatform(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(platformService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPlatform(@RequestBody @Valid final PlatformDTO platformDTO) {
        final Long createdId = platformService.create(platformDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePlatform(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PlatformDTO platformDTO) {
        platformService.update(id, platformDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlatform(@PathVariable(name = "id") final Long id) {
        platformService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
