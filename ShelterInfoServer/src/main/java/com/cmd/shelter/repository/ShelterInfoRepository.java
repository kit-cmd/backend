package com.cmd.shelter.repository;

import com.cmd.shelter.Dto.Shelterinfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterInfoRepository extends JpaRepository<Shelterinfo, Long> {
    Page<Shelterinfo> findAll(Pageable pageable);
}
