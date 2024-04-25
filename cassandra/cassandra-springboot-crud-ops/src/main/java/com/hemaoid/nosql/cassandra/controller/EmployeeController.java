package com.hemaoid.nosql.cassandra.controller;

import com.hemaoid.nosql.cassandra.entity.Employee;
import com.hemaoid.nosql.cassandra.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Employee employee) {
        employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/all")
    public ResponseEntity<?> saveAll(@RequestBody @Valid List<Employee> employees) {
        employeeService.saveAll(employees);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{empId}")
    public ResponseEntity<Employee> updateById(@PathVariable("empId") @NotNull @Positive @Min(value=1) Integer empId, @RequestBody @Valid Employee employee) {
        return ResponseEntity.ok(employeeService.updateById(empId, employee));
    }

    @DeleteMapping(value = "/{empId}")
    public ResponseEntity<?> deleteById(@PathVariable("empId") @NotNull @Positive @Min(value=1) Integer empId) {
        employeeService.deleteById(empId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}