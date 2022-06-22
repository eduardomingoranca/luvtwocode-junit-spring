package com.brazil.luvtwocode.domain.model;

import com.brazil.luvtwocode.port.in.StudentPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "student")
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

    public String getFullName() {
        return getFirstname() + " " + getLastname();
    }

    public String studentInformation() {
        return getFullName() + " " + getEmailAddress();
    }

}
