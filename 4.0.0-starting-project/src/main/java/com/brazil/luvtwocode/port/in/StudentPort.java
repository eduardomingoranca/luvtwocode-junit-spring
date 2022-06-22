package com.brazil.luvtwocode.port.in;

import org.springframework.stereotype.Component;

@Component
public interface StudentPort {
    String studentInformation();

    String getFullName();
}
