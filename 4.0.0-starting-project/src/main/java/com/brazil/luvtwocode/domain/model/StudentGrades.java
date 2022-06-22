package com.brazil.luvtwocode.domain.model;

import com.brazil.luvtwocode.port.in.GradePort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@Setter
@Getter
@ToString
@Component
@NoArgsConstructor
public class StudentGrades {
    List<GradePort> mathGradeResults;

    List<GradePort> scienceGradeResults;

    List<GradePort> historyGradeResults;

    public double addGradeResultsForSingleClass(List<GradePort> grades) {
        double result = 0;
        for (GradePort i : grades) {
            result += i.getGrade();
        }
        return result;
    }

    public double findGradePointAverage (List<GradePort> grades ) {
        int lengthOfGrades = grades.size();
        double sum = addGradeResultsForSingleClass(grades);
        double result = sum / lengthOfGrades;

        // add a round function
        BigDecimal resultRound = valueOf(result);
        resultRound = resultRound.setScale(2, HALF_UP);
        return resultRound.doubleValue();
    }

}
