package com.brazil.ms_school.app.port.out;

import com.brazil.ms_school.app.domain.model.HistoryGrade;
import org.springframework.data.repository.CrudRepository;

public interface HistoryGradesPort extends CrudRepository<HistoryGrade, Integer> {
    Iterable<HistoryGrade> findGradeByStudentId(int id);

    void deleteByStudentId(int id);
}
