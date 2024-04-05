package com.bookingkols.webapp.repos;

import com.bookingkols.webapp.domain.Category;
import com.bookingkols.webapp.domain.Industry;
import com.bookingkols.webapp.domain.Influence;
import com.bookingkols.webapp.domain.Platform;
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
