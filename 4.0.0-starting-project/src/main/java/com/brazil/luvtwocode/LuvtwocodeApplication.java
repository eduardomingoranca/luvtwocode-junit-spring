package com.brazil.luvtwocode;

import com.brazil.luvtwocode.domain.model.CollegeStudent;
import com.brazil.luvtwocode.domain.model.HistoryGrade;
import com.brazil.luvtwocode.domain.model.MathGrade;
import com.brazil.luvtwocode.domain.model.ScienceGrade;
import com.brazil.luvtwocode.port.in.GradePort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class LuvtwocodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LuvtwocodeApplication.class, args);
	}

	@Bean
	@Scope(value = "prototype")
	CollegeStudent getCollegeStudent() {
		return new CollegeStudent();
	}

	@Bean
	@Scope(value = "prototype")
	GradePort getMathGrade(double grade) {
		return new MathGrade(grade);
	}

	@Bean
	@Scope(value = "prototype")
	@Qualifier("mathGrades")
	MathGrade getGrade() {
		return new MathGrade();
	}

	@Bean
	@Scope(value = "prototype")
	@Qualifier("scienceGrades")
	ScienceGrade getScienceGrade() {
		return new ScienceGrade();
	}

	@Bean
	@Scope(value = "prototype")
	@Qualifier("historyGrades")
	HistoryGrade getHistoryGrade() {
		return new HistoryGrade();
	}

}
