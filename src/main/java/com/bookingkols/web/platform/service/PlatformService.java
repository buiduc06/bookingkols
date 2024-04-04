package com.bookingkols.web.platform.service;

import com.bookingkols.web.influence.repos.InfluenceRepository;
import com.bookingkols.web.platform.domain.Platform;
import com.bookingkols.web.platform.model.PlatformDTO;
import com.bookingkols.web.platform.repos.PlatformRepository;
import com.bookingkols.web.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class PlatformService {

    private final PlatformRepository platformRepository;
    private final InfluenceRepository influenceRepository;

    public PlatformService(final PlatformRepository platformRepository,
            final InfluenceRepository influenceRepository) {
        this.platformRepository = platformRepository;
        this.influenceRepository = influenceRepository;
    }

    public List<PlatformDTO> findAll() {
        final List<Platform> platforms = platformRepository.findAll(Sort.by("id"));
        return platforms.stream()
                .map(platform -> mapToDTO(platform, new PlatformDTO()))
                .toList();
    }

    public PlatformDTO get(final Long id) {
        return platformRepository.findById(id)
                .map(platform -> mapToDTO(platform, new PlatformDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlatformDTO platformDTO) {
        final Platform platform = new Platform();
        mapToEntity(platformDTO, platform);
        return platformRepository.save(platform).getId();
    }

    public void update(final Long id, final PlatformDTO platformDTO) {
        final Platform platform = platformRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(platformDTO, platform);
        platformRepository.save(platform);
    }

    public void delete(final Long id) {
        final Platform platform = platformRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        influenceRepository.findAllByPlatforms(platform)
                .forEach(influence -> influence.getPlatforms().remove(platform));
        platformRepository.delete(platform);
    }

    private PlatformDTO mapToDTO(final Platform platform, final PlatformDTO platformDTO) {
        platformDTO.setId(platform.getId());
        platformDTO.setName(platform.getName());
        platformDTO.setIcon(platform.getIcon());
        return platformDTO;
    }

    private Platform mapToEntity(final PlatformDTO platformDTO, final Platform platform) {
        platform.setName(platformDTO.getName());
        platform.setIcon(platformDTO.getIcon());
        return platform;
    }

}
