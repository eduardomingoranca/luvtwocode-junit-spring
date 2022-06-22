package com.brazil.luvtwocode.port.out;

import com.brazil.luvtwocode.domain.model.HistoryGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryGradesDaoPort  extends CrudRepository<HistoryGrade, Integer> {
     Iterable<HistoryGrade> findGradeByStudentId (int id);

     void deleteByStudentId(int id);
}
