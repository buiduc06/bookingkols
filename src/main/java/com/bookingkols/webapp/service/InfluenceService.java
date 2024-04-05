package com.bookingkols.webapp.service;

import com.bookingkols.webapp.domain.Category;
import com.bookingkols.webapp.domain.Industry;
import com.bookingkols.webapp.domain.Influence;
import com.bookingkols.webapp.domain.Platform;
import com.bookingkols.webapp.domain.Video;
import com.bookingkols.webapp.model.InfluenceDTO;
import com.bookingkols.webapp.repos.CategoryRepository;
import com.bookingkols.webapp.repos.IndustryRepository;
import com.bookingkols.webapp.repos.InfluenceRepository;
import com.bookingkols.webapp.repos.PlatformRepository;
import com.bookingkols.webapp.repos.VideoRepository;
import com.bookingkols.webapp.util.NotFoundException;
import com.bookingkols.webapp.util.ReferencedWarning;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class InfluenceService {

    private final InfluenceRepository influenceRepository;
    private final PlatformRepository platformRepository;
    private final CategoryRepository categoryRepository;
    private final IndustryRepository industryRepository;
    private final VideoRepository videoRepository;

    public InfluenceService(final InfluenceRepository influenceRepository,
            final PlatformRepository platformRepository,
            final CategoryRepository categoryRepository,
            final IndustryRepository industryRepository, final VideoRepository videoRepository) {
        this.influenceRepository = influenceRepository;
        this.platformRepository = platformRepository;
        this.categoryRepository = categoryRepository;
        this.industryRepository = industryRepository;
        this.videoRepository = videoRepository;
    }

    public List<InfluenceDTO> findAll() {
        final List<Influence> influences = influenceRepository.findAll(Sort.by("id"));
        return influences.stream()
                .map(influence -> mapToDTO(influence, new InfluenceDTO()))
                .toList();
    }

    public InfluenceDTO get(final Long id) {
        return influenceRepository.findById(id)
                .map(influence -> mapToDTO(influence, new InfluenceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final InfluenceDTO influenceDTO) {
        final Influence influence = new Influence();
        mapToEntity(influenceDTO, influence);
        return influenceRepository.save(influence).getId();
    }

    public void update(final Long id, final InfluenceDTO influenceDTO) {
        final Influence influence = influenceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(influenceDTO, influence);
        influenceRepository.save(influence);
    }

    public void delete(final Long id) {
        influenceRepository.deleteById(id);
    }

    private InfluenceDTO mapToDTO(final Influence influence, final InfluenceDTO influenceDTO) {
        influenceDTO.setId(influence.getId());
        influenceDTO.setName(influence.getName());
        influenceDTO.setGender(influence.getGender());
        influenceDTO.setHeight(influence.getHeight());
        influenceDTO.setWeight(influence.getWeight());
        influenceDTO.setCostMin(influence.getCostMin());
        influenceDTO.setCostMax(influence.getCostMax());
        influenceDTO.setProfilePicture(influence.getProfilePicture());
        influenceDTO.setIntroduce(influence.getIntroduce());
        influenceDTO.setStatus(influence.getStatus());
        influenceDTO.setPlatforms(influence.getPlatforms().stream()
                .map(platform -> platform.getId())
                .toList());
        influenceDTO.setCategories(influence.getCategories().stream()
                .map(category -> category.getId())
                .toList());
        influenceDTO.setIndustries(influence.getIndustries().stream()
                .map(industry -> industry.getId())
                .toList());
        return influenceDTO;
    }

    private Influence mapToEntity(final InfluenceDTO influenceDTO, final Influence influence) {
        influence.setName(influenceDTO.getName());
        influence.setGender(influenceDTO.getGender());
        influence.setHeight(influenceDTO.getHeight());
        influence.setWeight(influenceDTO.getWeight());
        influence.setCostMin(influenceDTO.getCostMin());
        influence.setCostMax(influenceDTO.getCostMax());
        influence.setProfilePicture(influenceDTO.getProfilePicture());
        influence.setIntroduce(influenceDTO.getIntroduce());
        influence.setStatus(influenceDTO.getStatus());
        final List<Platform> platforms = platformRepository.findAllById(
                influenceDTO.getPlatforms() == null ? Collections.emptyList() : influenceDTO.getPlatforms());
        if (platforms.size() != (influenceDTO.getPlatforms() == null ? 0 : influenceDTO.getPlatforms().size())) {
            throw new NotFoundException("one of platforms not found");
        }
        influence.setPlatforms(new HashSet<>(platforms));
        final List<Category> categories = categoryRepository.findAllById(
                influenceDTO.getCategories() == null ? Collections.emptyList() : influenceDTO.getCategories());
        if (categories.size() != (influenceDTO.getCategories() == null ? 0 : influenceDTO.getCategories().size())) {
            throw new NotFoundException("one of categories not found");
        }
        influence.setCategories(new HashSet<>(categories));
        final List<Industry> industries = industryRepository.findAllById(
                influenceDTO.getIndustries() == null ? Collections.emptyList() : influenceDTO.getIndustries());
        if (industries.size() != (influenceDTO.getIndustries() == null ? 0 : influenceDTO.getIndustries().size())) {
            throw new NotFoundException("one of industries not found");
        }
        influence.setIndustries(new HashSet<>(industries));
        return influence;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Influence influence = influenceRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Video influenceVideo = videoRepository.findFirstByInfluence(influence);
        if (influenceVideo != null) {
            referencedWarning.setKey("influence.video.influence.referenced");
            referencedWarning.addParam(influenceVideo.getId());
            return referencedWarning;
        }
        return null;
    }

}
