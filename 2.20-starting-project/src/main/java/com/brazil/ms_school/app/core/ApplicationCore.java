package com.brazil.ms_school.app.core;

import com.brazil.ms_school.app.adapter.ApplicationAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ApplicationCore {

    @Autowired
    private ApplicationAdapter applicationAdapter;

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
