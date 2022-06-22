package com.brazil.luvtwocode.port.out;


import com.brazil.luvtwocode.domain.model.ScienceGrade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScienceGradesDaoPort extends CrudRepository<ScienceGrade, Integer> {
     Iterable<ScienceGrade> findGradeByStudentId (int id);

     void deleteByStudentId(int id);
}
