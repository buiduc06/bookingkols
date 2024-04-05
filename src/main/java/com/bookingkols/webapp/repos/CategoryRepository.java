package com.bookingkols.webapp.repos;

import com.bookingkols.webapp.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
