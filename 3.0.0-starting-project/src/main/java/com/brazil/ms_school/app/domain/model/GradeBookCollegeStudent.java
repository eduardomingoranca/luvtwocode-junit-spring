package com.brazil.ms_school.app.domain.model;

import com.brazil.ms_school.app.domain.core.StudentGradesCore;

public class GradeBookCollegeStudent extends CollegeStudent {

    private int id;

    private StudentGradesCore studentGradesCore;

    public GradeBookCollegeStudent(String firstname, String lastname, String emailAddress) {
        super(firstname, lastname, emailAddress);
    }

    public GradeBookCollegeStudent(int id, String firstname, String lastname, String emailAddress, StudentGradesCore studentGradesCore) {
        super(firstname, lastname, emailAddress);
        this.studentGradesCore = studentGradesCore;
        this.id = id;
    }

    public StudentGradesCore getStudentGrades() {
        return studentGradesCore;
    }

    public void setStudentGrades(StudentGradesCore studentGradesCore) {
        this.studentGradesCore = studentGradesCore;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
