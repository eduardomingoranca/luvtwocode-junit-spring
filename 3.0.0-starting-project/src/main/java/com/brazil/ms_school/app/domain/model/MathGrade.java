package com.brazil.ms_school.app.domain.model;

import com.brazil.ms_school.app.port.out.GradePort;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "math_grade")
@NoArgsConstructor
public class MathGrade implements GradePort {

    @Id
    private int id;

    @Column(name="student_id")
    private int studentId;

    @Column(name="grade")
    private double grade;

    public MathGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public double getGrade() {
        return this.grade;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getStudentId() {
        return this.studentId;
    }

    @Override
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public void setGrade(double grade) {
        this.grade = grade;
    }
}
