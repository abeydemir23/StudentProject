package com.alicanbeydemir.studentproject.controller;

import com.alicanbeydemir.studentproject.repository.CityRepository;
import com.alicanbeydemir.studentproject.repository.DistrictRepository;
import com.alicanbeydemir.studentproject.domain.Student;
import com.alicanbeydemir.studentproject.repository.StudentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsController {

    private final StudentRepository studentRepository;
    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;

    public StudentsController(StudentRepository studentRepository, CityRepository cityRepository, DistrictRepository districtRepository) {
        this.studentRepository = studentRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity addStudent(@RequestBody StudentDto student) throws URISyntaxException {
        Student newStudent = new Student();
        newStudent.setCity(cityRepository.findByCityName(student.getCity()));
        newStudent.setDistrict(districtRepository.findByDistrictName(student.getDistrict()));
        newStudent.setName(student.getName());
        newStudent.setPhoneNumber(student.getPhoneNumber());
        newStudent.setTckn(student.getTckn());
        Student returnStudent = studentRepository.save(newStudent);
        return ResponseEntity.created(new URI("/stutends/" + returnStudent.getId())).body(returnStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateStudent(@PathVariable Long id, @RequestBody StudentDto student) {
        Student currentStudent = studentRepository.findById(id).orElseThrow(RuntimeException::new);
        currentStudent.setCity(cityRepository.findByCityName(student.getCity()));
        currentStudent.setDistrict(districtRepository.findByDistrictName(student.getDistrict()));
        currentStudent.setName(student.getName());
        currentStudent.setPhoneNumber(student.getPhoneNumber());
        currentStudent.setTckn(student.getTckn());
        Student updatedStudent = studentRepository.save(currentStudent);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
