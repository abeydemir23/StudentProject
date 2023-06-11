package com.alicanbeydemir.studentproject.controller;

import com.alicanbeydemir.studentproject.repository.DistrictRepository;
import com.alicanbeydemir.studentproject.domain.District;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/districts")
public class DistrictController {

    private DistrictRepository districtRepository;

    public DistrictController(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }


    @GetMapping("/{id}")
    public List<District> getDistrict(@PathVariable Long id) {
        return districtRepository.findByCityId(id);
    }
}
