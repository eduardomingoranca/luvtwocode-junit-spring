package com.brazil.ms_school.app.port.in;

import com.brazil.ms_school.app.domain.model.CollegeStudent;
import org.springframework.ui.Model;

public interface GradeBookPort {
    String getStudents(Model m);
    String createStudent(CollegeStudent student, Model m);
    String deleteStudent(int id, Model m);
    String studentInformation(int id, Model m);
}
