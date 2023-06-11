package com.alicanbeydemir.studentproject;

import com.alicanbeydemir.studentproject.domain.TurkishCityData;
import com.alicanbeydemir.studentproject.repository.CityRepository;
import com.alicanbeydemir.studentproject.repository.DistrictRepository;
import com.alicanbeydemir.studentproject.domain.City;
import com.alicanbeydemir.studentproject.domain.District;
import com.alicanbeydemir.studentproject.repository.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;


import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class BoostrapInitialData implements CommandLineRunner {
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;

    public BoostrapInitialData(CityRepository cityRepository, DistrictRepository districtRepository) {
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
    }

    @Override
    public void run(String... args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        TypeReference<List<TurkishCityData>> typeReference = new TypeReference<>() {
        };
        File file = ResourceUtils.getFile("classpath:full.json");
        InputStream targetStream = Files.newInputStream(file.toPath());
        try {
            List<TurkishCityData> cityDataList = mapper.readValue(targetStream,typeReference);

            List<TurkishCityData> flattedCityDataList = cityDataList.stream()
                    .filter(distinctByKey(p -> p.getPlaka()))
                    .collect(Collectors.toList());



            for (TurkishCityData cityData : flattedCityDataList) {
                City newCity = new City();
                String il = cityData.getIl();
                newCity.setCityName(il);
                newCity.setId(cityData.getPlaka());
                cityRepository.save(newCity);
                List<District> districts = cityDataList.stream()
                        .filter(p-> p.getIl().equals(il))
                        .map(p-> {
                            District newDistrict = new District();
                            newDistrict.setCity(newCity);
                            newDistrict.setDistrictName(p.getIlce());
                            newDistrict.setId(p.getNviid());
                            districtRepository.save(newDistrict);
                            return newDistrict;
                        })
                        .collect(Collectors.toList());
                newCity.setDistricts(districts);
            }

        } catch (Exception e) {
            System.err.println("Unable to save cities: " + e.getMessage());
        }
    }

    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
