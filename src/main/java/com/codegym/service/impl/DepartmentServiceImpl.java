package com.codegym.service.impl;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.repository.DepartmentRepository;
import com.codegym.repository.EmployeeRepository;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(Long id) {

        return departmentRepository.findOne(id);
    }

    @Override
    public void save(Department department) {
        departmentRepository.save(department);

    }

    @Override
    public void remove(Long id) {
        departmentRepository.delete(id);

    }

    @Override
    public Page<Department> findAll(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }
    public Iterable<Employee> findAllByDepartment(Department department) {
        List<Employee> employees = (List<Employee>) employeeRepository.findAllByDepartment(department);
        return employees;
    }
}
