package com.merc.tech.churchadminservice.repository;

import com.merc.tech.churchadminservice.model.Church;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChurchRepository extends JpaRepository <Church, Long> {
}
