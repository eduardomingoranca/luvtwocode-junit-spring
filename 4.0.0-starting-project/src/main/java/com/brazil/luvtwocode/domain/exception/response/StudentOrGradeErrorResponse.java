package com.brazil.luvtwocode.domain.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentOrGradeErrorResponse {
    private int status;

    private String message;

    private long timeStamp;

}
