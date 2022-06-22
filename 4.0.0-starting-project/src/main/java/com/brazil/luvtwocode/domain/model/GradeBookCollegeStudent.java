package com.brazil.luvtwocode.domain.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GradeBookCollegeStudent extends CollegeStudent {
    private int id;

    private StudentGrades studentGrades;

    public GradeBookCollegeStudent(String firstname, String lastname, String emailAddress) {
        super(firstname, lastname, emailAddress);
    }

    public GradeBookCollegeStudent(int id, String firstname, String lastname, String emailAddress, StudentGrades studentGrades) {
        super(firstname, lastname, emailAddress);
        this.studentGrades = studentGrades;
        this.id = id;
    }


}
