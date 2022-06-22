package com.brazil.luvtwocode.port.in;

import org.springframework.stereotype.Component;

@Component
public interface GradePort {
    double getGrade();

    int getId();

    void setId(int id);

    int getStudentId();

    void setStudentId(int studentId);

    void setGrade(double grade);
}
