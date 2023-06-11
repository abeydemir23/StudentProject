package com.alicanbeydemir.studentproject.repository;

import com.alicanbeydemir.studentproject.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
