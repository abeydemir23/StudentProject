package com.alicanbeydemir.studentproject.controller;

import com.alicanbeydemir.studentproject.repository.CityRepository;
import com.alicanbeydemir.studentproject.domain.City;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public List<City> get() {
        return cityRepository.findAll();
    }
}
