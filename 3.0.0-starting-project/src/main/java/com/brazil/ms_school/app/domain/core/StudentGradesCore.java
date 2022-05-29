package com.brazil.ms_school.app.domain.core;

import com.brazil.ms_school.app.port.out.GradePort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

@Component
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentGradesCore {
    private List<GradePort> mathGradeResults;

    private List<GradePort> scienceGradeResults;

    private List<GradePort> historyGradeResults;

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
        BigDecimal resultRound = BigDecimal.valueOf(result);
        resultRound = resultRound.setScale(2, HALF_UP);
        return resultRound.doubleValue();

    }

}
