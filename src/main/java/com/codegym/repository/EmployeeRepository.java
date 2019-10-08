package com.codegym.repository;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e ORDER BY e.salary DESC")
    Page<Employee> findAll(Pageable pageable);
    Iterable<Employee> findAllByDepartment(Department department);

    Page<Employee> findAllByNameContaining(String name, Pageable pageable);
}
