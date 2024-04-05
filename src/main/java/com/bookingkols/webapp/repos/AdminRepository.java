package com.bookingkols.webapp.repos;

import com.bookingkols.webapp.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, Long> {
}
