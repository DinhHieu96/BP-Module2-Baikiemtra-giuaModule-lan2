package com.codegym.service;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DepartmentService {
    Iterable<Department> findAll();
    Page<Department> findAll(Pageable pageable);
    Department findById(Long id);
    void remove(Long id);

    void save(Department department);


    Iterable<Employee> findAllByDepartment(Department department);
}
