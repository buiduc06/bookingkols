package com.bookingkols.web.industry.service;

import com.bookingkols.web.industry.domain.Industry;
import com.bookingkols.web.industry.model.IndustryDTO;
import com.bookingkols.web.industry.repos.IndustryRepository;
import com.bookingkols.web.influence.repos.InfluenceRepository;
import com.bookingkols.web.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class IndustryService {

    private final IndustryRepository industryRepository;
    private final InfluenceRepository influenceRepository;

    public IndustryService(final IndustryRepository industryRepository,
            final InfluenceRepository influenceRepository) {
        this.industryRepository = industryRepository;
        this.influenceRepository = influenceRepository;
    }

    public List<IndustryDTO> findAll() {
        final List<Industry> industries = industryRepository.findAll(Sort.by("id"));
        return industries.stream()
                .map(industry -> mapToDTO(industry, new IndustryDTO()))
                .toList();
    }

    public IndustryDTO get(final Long id) {
        return industryRepository.findById(id)
                .map(industry -> mapToDTO(industry, new IndustryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final IndustryDTO industryDTO) {
        final Industry industry = new Industry();
        mapToEntity(industryDTO, industry);
        return industryRepository.save(industry).getId();
    }

    public void update(final Long id, final IndustryDTO industryDTO) {
        final Industry industry = industryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(industryDTO, industry);
        industryRepository.save(industry);
    }

    public void delete(final Long id) {
        final Industry industry = industryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        influenceRepository.findAllByIndustries(industry)
                .forEach(influence -> influence.getIndustries().remove(industry));
        industryRepository.delete(industry);
    }

    private IndustryDTO mapToDTO(final Industry industry, final IndustryDTO industryDTO) {
        industryDTO.setId(industry.getId());
        industryDTO.setName(industry.getName());
        industryDTO.setIcon(industry.getIcon());
        return industryDTO;
    }

    private Industry mapToEntity(final IndustryDTO industryDTO, final Industry industry) {
        industry.setName(industryDTO.getName());
        industry.setIcon(industryDTO.getIcon());
        return industry;
    }

}
