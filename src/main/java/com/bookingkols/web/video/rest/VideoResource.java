package com.bookingkols.web.video.rest;

import com.bookingkols.web.influence.domain.Influence;
import com.bookingkols.web.influence.repos.InfluenceRepository;
import com.bookingkols.web.util.CustomCollectors;
import com.bookingkols.web.video.model.VideoDTO;
import com.bookingkols.web.video.service.VideoService;
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
@RequestMapping(value = "/api/videos", produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoResource {

    private final VideoService videoService;
    private final InfluenceRepository influenceRepository;

    public VideoResource(final VideoService videoService,
            final InfluenceRepository influenceRepository) {
        this.videoService = videoService;
        this.influenceRepository = influenceRepository;
    }

    @GetMapping
    public ResponseEntity<List<VideoDTO>> getAllVideos() {
        return ResponseEntity.ok(videoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoDTO> getVideo(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(videoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createVideo(@RequestBody @Valid final VideoDTO videoDTO) {
        final Long createdId = videoService.create(videoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateVideo(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final VideoDTO videoDTO) {
        videoService.update(id, videoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteVideo(@PathVariable(name = "id") final Long id) {
        videoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/influenceValues")
    public ResponseEntity<Map<Long, String>> getInfluenceValues() {
        return ResponseEntity.ok(influenceRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Influence::getId, Influence::getName)));
    }

}
