package com.brazil.ms_school.app.port.out;

import com.brazil.ms_school.app.domain.model.MathGrade;
import org.springframework.data.repository.CrudRepository;

public interface MathGradesPort extends CrudRepository<MathGrade, Integer> {
    Iterable<MathGrade> findGradeByStudentId(int id);
}
