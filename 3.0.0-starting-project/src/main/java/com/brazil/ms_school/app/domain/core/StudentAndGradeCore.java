package com.brazil.ms_school.app.domain.core;

import com.brazil.ms_school.app.domain.model.CollegeStudent;
import com.brazil.ms_school.app.port.out.StudentRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
/*
 transacional - porque permitira que o spring gerencie as
 transacoes em segundo plano.
 */
@Transactional
public class StudentAndGradeCore {

    @Autowired
    private StudentRepositoryPort studentRepositoryPort;

    public void createStudent(String firstname, String lastname, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstname, lastname, emailAddress);
        student.setId(1);
        studentRepositoryPort.save(student);
    }

    public boolean checkIfStudentIsNull(int id) {
        Optional<CollegeStudent> student = studentRepositoryPort.findById(id);
        if (student.isPresent()) {
            return true;
        }
        return false;
    }

    public void deleteStudent(int id) {
        if (checkIfStudentIsNull(id)) {
            studentRepositoryPort.deleteById(id);
        }
    }

    public Iterable<CollegeStudent> getGradebook() {
        Iterable<CollegeStudent> collegeStudents = studentRepositoryPort.findAll();
        return collegeStudents;
    }

}
