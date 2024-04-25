package com.hemaoid.nosql.cassandra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ApiError {
    private Integer httpStatusCode;
    private String errorMessage;
}
