package com.example.exersice7.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class ApiResponse {

    private String msg;
   private Integer status;
}
