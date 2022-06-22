package com.brazil.luvtwocode.port.out;

import com.brazil.luvtwocode.domain.model.MathGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathGradesDaoPort extends CrudRepository<MathGrade, Integer> {
     Iterable<MathGrade> findGradeByStudentId (int id);

     void deleteByStudentId(int id);
}
