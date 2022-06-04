package com.brazil.ms_school.app.domain.model;

import com.brazil.ms_school.app.port.out.StudentPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class CollegeStudent implements StudentPort {

    @Id
    private int id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column(name="email_address")
    private String emailAddress;

    public CollegeStudent(String firstname, String lastname, String emailAddress) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
    }

    @Override
    public String studentInformation() {
        return getFullName() + " " + getEmailAddress();
    }

    @Override
    public String getFullName() {
        return getFirstname() + " " + getLastname();
    }
}
