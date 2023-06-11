package com.alicanbeydemir.studentproject.repository;

import com.alicanbeydemir.studentproject.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Long> {

    List<District> findByCityId(Long cityId);
    District findByDistrictName(String districtName);
}
