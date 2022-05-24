package com.example.exersice7.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@AllArgsConstructor @Data
public class Employee {

    @NotNull(message = "The id is required")
    @Min(value = 2, message = "Length must be more than 2")
    private Integer ID ;

    @NotNull(message = "The name is required")
    @Min(value = 4, message = "Length must be more than 4")
    private String name;

    @NotNull(message = "The age is required")
    @Digits(integer = 3, fraction = 2)
    @Min(value = 25, message = "Length must be more than 25")
    private Integer age ;

    @Pattern(regexp = "^(false)$")
    private Boolean onLeave;

    @NotNull(message = "The employment year is required")
    @Digits(integer = 4, fraction = 0)
    @Min(value = 1989, message = "Year must be more than 1989")
    @Max(value = 2022, message = "Year must be less than 1989")
    private Integer employmentYear ;

    @NotNull(message = "The annual leave is required")
    @Digits(integer = 4, fraction = 0)
    private Integer annualLeave;



}
