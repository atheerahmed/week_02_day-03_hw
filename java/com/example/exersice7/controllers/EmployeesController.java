package com.example.exersice7.controllers;

import com.example.exersice7.model.ApiResponse;
import com.example.exersice7.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeesController {

    ArrayList<Employee> employees = new ArrayList<Employee>();

    @GetMapping
    public ResponseEntity getEmployees() {
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping
    public ResponseEntity addEmployees(@RequestBody @Valid Employee employees,
                                   Errors errors) {
        return getResponseEntity(employees, errors);
    }

    @PutMapping
    public ResponseEntity updateEmployees(@RequestBody @Valid Employee employee,
                                          Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            ApiResponse apiResponse = new ApiResponse(message, 400);
            return ResponseEntity.status(400).body(apiResponse);
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getID() == employee.getID()) {
                employees.add(i, employee);
                return ResponseEntity.status(200).body(new ApiResponse("The employee is updated", 200));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("The index is not exist", 400));
    }

    @DeleteMapping("/{index}")
    public ResponseEntity deleteEmployees(@PathVariable @Valid Integer index, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            ApiResponse apiResponse = new ApiResponse(message, 400);
            return ResponseEntity.status(400).body(apiResponse);
        }
        if (index < employees.size()|| index >= 0) {
            employees.remove((int) index);
                return ResponseEntity.status(200).body(new ApiResponse("The employee is deleted", 200));
            }

        return ResponseEntity.status(400).body(new ApiResponse("The index is not exist", 400));
    }


    private ResponseEntity getResponseEntity(@RequestBody @Valid Employee employee,
                                             Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            ApiResponse apiResponse = new ApiResponse(message, 400);
            return ResponseEntity.status(400).body(apiResponse);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity annualLeave(@PathVariable @Valid Integer id) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getID() == id) {
                if (employees.get(i).getAnnualLeave() == 0)
                    return ResponseEntity.status(400).body(new ApiResponse("The annual leave not accepted", 400));
                if(employees.get(i).getOnLeave().equals(true))
                    return ResponseEntity.status(400).body(new ApiResponse("Not accepted you are in leave", 400));
                employees.get(i).setOnLeave(true);
                Integer newAnnualLeave = employees.get(i).getAnnualLeave() - 1;
                employees.get(i).setAnnualLeave(newAnnualLeave);
            }
        }
        return ResponseEntity.status(200).body(new ApiResponse("The annual leave is accepted", 200));
    }


}
