package com.bookingkols.web.video.service;

import com.bookingkols.web.influence.domain.Influence;
import com.bookingkols.web.influence.repos.InfluenceRepository;
import com.bookingkols.web.util.NotFoundException;
import com.bookingkols.web.video.domain.Video;
import com.bookingkols.web.video.model.VideoDTO;
import com.bookingkols.web.video.repos.VideoRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final InfluenceRepository influenceRepository;

    public VideoService(final VideoRepository videoRepository,
            final InfluenceRepository influenceRepository) {
        this.videoRepository = videoRepository;
        this.influenceRepository = influenceRepository;
    }

    public List<VideoDTO> findAll() {
        final List<Video> videos = videoRepository.findAll(Sort.by("id"));
        return videos.stream()
                .map(video -> mapToDTO(video, new VideoDTO()))
                .toList();
    }

    public VideoDTO get(final Long id) {
        return videoRepository.findById(id)
                .map(video -> mapToDTO(video, new VideoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final VideoDTO videoDTO) {
        final Video video = new Video();
        mapToEntity(videoDTO, video);
        return videoRepository.save(video).getId();
    }

    public void update(final Long id, final VideoDTO videoDTO) {
        final Video video = videoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(videoDTO, video);
        videoRepository.save(video);
    }

    public void delete(final Long id) {
        videoRepository.deleteById(id);
    }

    private VideoDTO mapToDTO(final Video video, final VideoDTO videoDTO) {
        videoDTO.setId(video.getId());
        videoDTO.setFileName(video.getFileName());
        videoDTO.setFileType(video.getFileType());
        videoDTO.setFileSize(video.getFileSize());
        videoDTO.setFilePath(video.getFilePath());
        videoDTO.setInfluence(video.getInfluence() == null ? null : video.getInfluence().getId());
        return videoDTO;
    }

    private Video mapToEntity(final VideoDTO videoDTO, final Video video) {
        video.setFileName(videoDTO.getFileName());
        video.setFileType(videoDTO.getFileType());
        video.setFileSize(videoDTO.getFileSize());
        video.setFilePath(videoDTO.getFilePath());
        final Influence influence = videoDTO.getInfluence() == null ? null : influenceRepository.findById(videoDTO.getInfluence())
                .orElseThrow(() -> new NotFoundException("influence not found"));
        video.setInfluence(influence);
        return video;
    }

}
