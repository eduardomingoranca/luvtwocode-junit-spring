package com.brazil.ms_school.app.port.out;

import com.brazil.ms_school.app.domain.model.ScienceGrade;
import org.springframework.data.repository.CrudRepository;

public interface ScienceGradesPort extends CrudRepository<ScienceGrade, Integer> {
    Iterable<ScienceGrade> findGradeByStudentId(int id);
}
