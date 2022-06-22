package com.brazil.luvtwocode.domain.model;

import com.brazil.luvtwocode.port.in.GradePort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "science_grade")
public class ScienceGrade implements GradePort {

    @Id
    private int id;

    @Column(name="student_id")
    private int studentId;

    @Column(name="grade")
    private double grade;

    public ScienceGrade(double grade) {
        this.grade = grade;
    }

}
