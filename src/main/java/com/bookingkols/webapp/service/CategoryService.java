package com.bookingkols.webapp.service;

import com.bookingkols.webapp.domain.Category;
import com.bookingkols.webapp.model.CategoryDTO;
import com.bookingkols.webapp.repos.CategoryRepository;
import com.bookingkols.webapp.repos.InfluenceRepository;
import com.bookingkols.webapp.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final InfluenceRepository influenceRepository;

    public CategoryService(final CategoryRepository categoryRepository,
            final InfluenceRepository influenceRepository) {
        this.categoryRepository = categoryRepository;
        this.influenceRepository = influenceRepository;
    }

    public List<CategoryDTO> findAll() {
        final List<Category> categories = categoryRepository.findAll(Sort.by("id"));
        return categories.stream()
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .toList();
    }

    public CategoryDTO get(final Long id) {
        return categoryRepository.findById(id)
                .map(category -> mapToDTO(category, new CategoryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategoryDTO categoryDTO) {
        final Category category = new Category();
        mapToEntity(categoryDTO, category);
        return categoryRepository.save(category).getId();
    }

    public void update(final Long id, final CategoryDTO categoryDTO) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoryDTO, category);
        categoryRepository.save(category);
    }

    public void delete(final Long id) {
        final Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        influenceRepository.findAllByCategories(category)
                .forEach(influence -> influence.getCategories().remove(category));
        categoryRepository.delete(category);
    }

    private CategoryDTO mapToDTO(final Category category, final CategoryDTO categoryDTO) {
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setIcon(category.getIcon());
        return categoryDTO;
    }

    private Category mapToEntity(final CategoryDTO categoryDTO, final Category category) {
        category.setName(categoryDTO.getName());
        category.setIcon(categoryDTO.getIcon());
        return category;
    }

}
