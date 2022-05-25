package com.brazil.ms_school.app.core;

import com.brazil.ms_school.app.adapter.ApplicationAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ApplicationCore {

    private final ApplicationAdapter applicationAdapter;

    public double addGradeResultsForSingleClass(List<Double> numbers) {
        return applicationAdapter.addGradeResultsForSingleClass(numbers);
    }

    public double findGradePointAverage (List<Double> grades ) {
        return applicationAdapter.findGradePointAverage(grades);
    }

    public Object checkNull(Object obj) {
        return applicationAdapter.checkNull(obj);
    }
}
