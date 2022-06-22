package com.brazil.luvtwocode.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class GradeBook {
    List<GradeBookCollegeStudent> students = new ArrayList<>();
}
