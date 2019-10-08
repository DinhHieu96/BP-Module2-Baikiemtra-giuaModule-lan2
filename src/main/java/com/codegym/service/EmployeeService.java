package com.codegym.service;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    Page<Employee> findAll(Pageable pageable);
    Employee findById(Long id);
    void remove(Long id);
    Iterable<Employee> findAllByDepartment(Department department);


    void save(EmployeeForm employeeForm);

    Page<Employee> findAllByNameContaining(String name, Pageable pageable);

    Employee getEmployee(EmployeeForm employeeForm);


}
