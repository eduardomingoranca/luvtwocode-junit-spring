package com.brazil.ms_school;

import com.brazil.ms_school.domain.core.StudentGradesCore;
import com.brazil.ms_school.domain.models.CollegeStudent;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static java.util.Arrays.asList;

@SpringBootTest
@RequiredArgsConstructor
class ApplicationTests {

	private static int count = 0;

	@Value("${info.app.name}")
	private String appInfo;

	@Value("${info.app.description}")
	private String appDescription;

	@Value("${info.app.version}")
	private String appVersion;

	@Value("${info.school.name}")
	private String schoolName;

	@Autowired
	CollegeStudent collegeStudent;

	@Autowired
	StudentGradesCore studentGradesCore;

	@BeforeEach
	public void beforeEach() {
		count = count + 1;
		System.out.println("Testing: ".concat(appInfo).concat(" which is ")
				.concat(appDescription).concat(" Version: ").concat(appVersion)
				.concat(". Execution of test method ") + count);
		collegeStudent.setFirstname("Lon");
		collegeStudent.setLastname("Hammond");
		collegeStudent.setEmailAddress("lon.hammond@luv2code_school.com");
		studentGradesCore.setMathGradeResults(new ArrayList<>(asList(100.0, 85.0, 76.5, 91.75)));
		collegeStudent.setStudentGradesCore(studentGradesCore);
	}

	@Test
	void contextLoads() {
	}

}
