package com.codegym.service.impl;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import com.codegym.repository.EmployeeRepository;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@PropertySource("classpath:global_config_app.properties")
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    Environment env;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Page<Employee> findAll(Pageable pageable) {

        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee findById(Long id) {

        return employeeRepository.findOne(id);
    }

    @Override
    public void save(EmployeeForm employeeForm) {
        Employee employee=getEmployee(employeeForm);
        employeeRepository.save(employee);

    }

    @Override
    public void remove(Long id) {

        employeeRepository.delete(id);
    }

    @Override
    public Iterable<Employee> findAllByDepartment(Department department) {
        return employeeRepository.findAllByDepartment(department);
    }

    @Override
    public Page<Employee> findAllByNameContaining(String name, Pageable pageable) {
        return employeeRepository.findAllByNameContaining(name,pageable);

    }


    @Override
    public Employee getEmployee(EmployeeForm employeeForm) {
        // lay ten file
        MultipartFile multipartFile = employeeForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();
        // luu file len server
        try {
            //multipartFile.transferTo(imageFile);
            FileCopyUtils.copy(employeeForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (fileName.equals("") && employeeForm.getId() != null){
            Employee employee = findById(employeeForm.getId());
            fileName = employee.getAvatar();
        }
        // tham khảo: https://github.com/codegym-vn/spring-static-resources

        // tao doi tuong de luu vao db
        if (employeeForm.getId() == null) {
            return new Employee(employeeForm.getName(), employeeForm.getBirthDate(), employeeForm.getAddress(), fileName, employeeForm.getSalary(), employeeForm.getDepartment());
        } else {
            return new Employee(employeeForm.getId(),employeeForm.getName(), employeeForm.getBirthDate(), employeeForm.getAddress(), fileName, employeeForm.getSalary(), employeeForm.getDepartment());
        }

    }
}
