package com.brazil.ms_school.app.adapter;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class ApplicationAdapter {

    // adicionando cada uma das notas
    public double addGradeResultsForSingleClass(List<Double> grades) {
        double result = 0;
        for (double i : grades) {
            result += i;
        }
        return result;
    }

    // media das notas
    public double findGradePointAverage(List<Double> grades) {
        int lengthOfGrades = grades.size();
        double sum = addGradeResultsForSingleClass(grades);
        double result = sum / lengthOfGrades;

        // add a round function
        BigDecimal resultRound = valueOf(result);
        resultRound = resultRound.setScale(2, HALF_UP);
        return resultRound.doubleValue();

    }

    // verificando se o objeto e nulo
    public Object checkNull(Object obj) {
        if (obj != null) {
            return obj;
        }
        return null;
    }
}
