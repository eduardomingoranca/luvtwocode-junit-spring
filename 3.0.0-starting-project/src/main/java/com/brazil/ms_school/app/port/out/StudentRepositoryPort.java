package com.brazil.ms_school.app.port.out;

import com.brazil.ms_school.app.domain.model.CollegeStudent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositoryPort extends CrudRepository<CollegeStudent, Integer> {
    CollegeStudent findByEmailAddress(String emailAddress);
}
