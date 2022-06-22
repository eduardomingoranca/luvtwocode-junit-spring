package com.brazil.luvtwocode.port.out;

import com.brazil.luvtwocode.domain.model.CollegeStudent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDaoPort extends CrudRepository<CollegeStudent, Integer> {
    CollegeStudent findByEmailAddress(String emailAddress);
}
