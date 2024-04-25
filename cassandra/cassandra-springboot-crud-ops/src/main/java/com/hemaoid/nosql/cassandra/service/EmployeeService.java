package com.hemaoid.nosql.cassandra.service;

import com.hemaoid.nosql.cassandra.entity.Employee;
import com.hemaoid.nosql.cassandra.exception.EmployeeNotFoundException;
import com.hemaoid.nosql.cassandra.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public void saveAll(List<Employee> employees) {
        employeeRepository.saveAll(employees);
    }

    public Employee updateById(Integer empId, Employee employee) {
       Optional<Employee> optEmp = employeeRepository.findById(empId);
       optEmp.ifPresentOrElse(emp -> {
           BeanUtils.copyProperties(employee, emp);
           employeeRepository.save(emp);
       }, () -> { throw new EmployeeNotFoundException(String.format("Employee(Id=%d) record not found", empId)); });
       return optEmp.orElseGet(() -> employee);
    }

    public void deleteById(Integer empId) {
        Optional<Employee> optEmp = employeeRepository.findById(empId);
        optEmp.ifPresentOrElse(emp -> {
            employeeRepository.deleteById(empId);
        }, () -> { throw new EmployeeNotFoundException(String.format("Employee(Id=%d) record not found", empId)); });
    }
}
