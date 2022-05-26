package com.brazil.ms_school.app.model;

import com.brazil.ms_school.app.core.StudentGradesCore;
import com.brazil.ms_school.app.port.StudentPort;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CollegeStudent implements StudentPort {
    private int id;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private StudentGradesCore studentGradesCore;

    @Override
    public String studentInformation() {
        return getFullName() + " " + getEmailAddress();
    }

    @Override
    public String getFullName() {
        return getFirstname() + " " + getLastname();
    }

    private String getFirstNameAndId() {
        return getFirstname() + " " + getId();
    }

}
