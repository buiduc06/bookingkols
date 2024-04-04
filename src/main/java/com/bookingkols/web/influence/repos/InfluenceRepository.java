package com.bookingkols.web.influence.repos;

import com.bookingkols.web.category.domain.Category;
import com.bookingkols.web.industry.domain.Industry;
import com.bookingkols.web.influence.domain.Influence;
import com.bookingkols.web.platform.domain.Platform;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InfluenceRepository extends JpaRepository<Influence, Long> {

    Influence findFirstByPlatforms(Platform platform);

    Influence findFirstByCategories(Category category);

    Influence findFirstByIndustries(Industry industry);

    List<Influence> findAllByPlatforms(Platform platform);

    List<Influence> findAllByCategories(Category category);

    List<Influence> findAllByIndustries(Industry industry);

}
