package com.brazil.ms_school.models;

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
public class StudentGrades {
    private List<Grade> mathGradeResults;

    private List<Grade> scienceGradeResults;

    private List<Grade> historyGradeResults;

    public double addGradeResultsForSingleClass(List<Grade> grades) {
        double result = 0;
        for (Grade i : grades) {
            result += i.getGrade();
        }
        return result;
    }

    public double findGradePointAverage (List<Grade> grades ) {
        int lengthOfGrades = grades.size();
        double sum = addGradeResultsForSingleClass(grades);
        double result = sum / lengthOfGrades;

        // add a round function
        BigDecimal resultRound = BigDecimal.valueOf(result);
        resultRound = resultRound.setScale(2, HALF_UP);
        return resultRound.doubleValue();

    }

}
