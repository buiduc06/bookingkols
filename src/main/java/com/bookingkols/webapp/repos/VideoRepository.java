package com.bookingkols.webapp.repos;

import com.bookingkols.webapp.domain.Influence;
import com.bookingkols.webapp.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VideoRepository extends JpaRepository<Video, Long> {

    Video findFirstByInfluence(Influence influence);

}
