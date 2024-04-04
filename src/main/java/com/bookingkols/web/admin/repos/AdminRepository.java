package com.bookingkols.web.admin.repos;

import com.bookingkols.web.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository extends JpaRepository<Admin, Long> {
}
