package com.bookingkols.web.video.repos;

import com.bookingkols.web.influence.domain.Influence;
import com.bookingkols.web.video.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VideoRepository extends JpaRepository<Video, Long> {

    Video findFirstByInfluence(Influence influence);

}
