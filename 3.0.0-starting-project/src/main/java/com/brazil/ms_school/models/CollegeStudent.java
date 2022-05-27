package com.brazil.ms_school.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
public class CollegeStudent implements Student {

    @Id
    @GeneratedValue(strategy= IDENTITY)
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
