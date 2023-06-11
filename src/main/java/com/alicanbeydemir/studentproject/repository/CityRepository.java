package com.alicanbeydemir.studentproject.repository;

import com.alicanbeydemir.studentproject.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByCityName(String name);
}

