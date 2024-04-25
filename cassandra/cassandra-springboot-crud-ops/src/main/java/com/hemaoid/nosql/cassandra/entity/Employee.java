package com.hemaoid.nosql.cassandra.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("employee")
@Setter
@Getter
public class Employee {

    @NotNull
    @Positive
    @Min(value = 1)
    @PrimaryKey
    private Integer id;

    @NotBlank
    @Size(min = 3)
    private String name;

    @NotNull
    @Positive
    @Min(value = 1)
    private Integer salary;
}
