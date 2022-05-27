package com.brazil.ms_school.service;

import com.brazil.ms_school.models.CollegeStudent;
import com.brazil.ms_school.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
/*
 transacional - porque permitira que o spring gerencie as
 transacoes em segundo plano.
 */
@Transactional
public class StudentAndGradeService {

    @Autowired
    private StudentDao studentDao;

    public void createStudent(String firstname, String lastname, String emailAddress) {
        CollegeStudent student = new CollegeStudent(firstname, lastname, emailAddress);
        student.setId(0);
        studentDao.save(student);
    }

}
