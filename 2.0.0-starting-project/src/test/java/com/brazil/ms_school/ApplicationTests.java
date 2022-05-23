package com.brazil.ms_school;

import com.brazil.ms_school.domain.core.StudentGradesCore;
import com.brazil.ms_school.domain.models.CollegeStudent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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

	@Autowired
	ApplicationContext context;

	@BeforeEach
	void beforeEach() {
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

	@DisplayName("Add grade results for student grades")
	@Test
	void addGradeResultsForStudentGrades() {
		assertEquals(353.25, studentGradesCore.addGradeResultsForSingleClass(
				collegeStudent.getStudentGradesCore().getMathGradeResults()
		));
	}

	@DisplayName("Add grade results for student grades not equal")
	@Test
	void addGradeResultsForStudentGradesAssertNotEquals() {
		assertNotEquals(0, studentGradesCore.addGradeResultsForSingleClass(
				collegeStudent.getStudentGradesCore().getMathGradeResults()
		));
	}

	@DisplayName("Is grade greater")
	@Test
	void isGradeGreaterStudentGrades() {
		assertTrue(studentGradesCore.isGradeGreater(90, 75)
				, "failure - should be true");
	}

	@DisplayName("Is grade greater false")
	@Test
	void isGradeGreaterStudentGradesAssertFalse() {
		assertFalse(studentGradesCore.isGradeGreater(89, 92)
				, "failure - should be false");
	}

	@DisplayName("Check Null for student grades")
	@Test
	void checkNullForStudentGrades() {
		assertNotNull(studentGradesCore.checkNull(collegeStudent.getStudentGradesCore()
				.getMathGradeResults()), "object should not be null");
	}

	@DisplayName("Create student without grade init")
	@Test
	void createStudentWithoutGradesInit() {
		CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
		studentTwo.setFirstname("Alan");
		studentTwo.setLastname("Hammond");
		studentTwo.setEmailAddress("alan.hammond@luv2code_school.com");
		assertNotNull(studentTwo.getFirstname());
		assertNotNull(studentTwo.getLastname());
		assertNotNull(studentTwo.getEmailAddress());
		assertNull(studentGradesCore.checkNull(studentTwo.getStudentGradesCore()));
	}

	@DisplayName("Verify students are prototypes")
	@Test
	void verifyStudentsArePrototypes() {
		CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
		assertNotSame(collegeStudent, studentTwo);
	}

	@DisplayName("Find Grade Point Average")
	@Test
	void findGradePointAverage() {
		assertAll("Testing all assertEquals",
				()-> assertEquals(353.25, studentGradesCore.addGradeResultsForSingleClass(
						collegeStudent.getStudentGradesCore().getMathGradeResults())),
				()-> assertEquals(88.31, studentGradesCore.findGradePointAverage(
						collegeStudent.getStudentGradesCore().getMathGradeResults()))
		);
	}

}
