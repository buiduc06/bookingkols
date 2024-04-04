package com.bookingkols.web.platform.repos;

import com.bookingkols.web.platform.domain.Platform;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlatformRepository extends JpaRepository<Platform, Long> {
}
