package com.alicanbeydemir.studentproject.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private String tckn;
    private String name;
    private String phoneNumber;
    private String city;
    private String district;
}
