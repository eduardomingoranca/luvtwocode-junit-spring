package com.brazil.ms_school;

import com.brazil.ms_school.app.adapter.ApplicationAdapter;
import com.brazil.ms_school.app.core.ApplicationCore;
import com.brazil.ms_school.app.model.CollegeStudent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class MsSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsSchoolApplication.class, args);
	}

	/* New for Section 2.2 */
	@Bean(name = "applicationExample")
	ApplicationCore getApplicationCore() {
		return new ApplicationCore();
	}

	/* New for Section 2.2 */
	@Bean(name = "applicationAdapter")
	ApplicationAdapter getApplicationAdapter() {
		return new ApplicationAdapter();
	}

	@Bean(name = "collegeStudent")
	@Scope(value = "prototype")
	CollegeStudent getCollegeStudent() {
		return new CollegeStudent();
	}
}
