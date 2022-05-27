package com.brazil.ms_school.repository;

import com.brazil.ms_school.models.CollegeStudent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends CrudRepository<CollegeStudent, Integer> {
    CollegeStudent findByEmailAddress(String emailAddress);
}
