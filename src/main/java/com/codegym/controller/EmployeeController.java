package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.model.EmployeeForm;
import com.codegym.service.DepartmentService;
import com.codegym.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    Environment env;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @ModelAttribute("departments")
    public Iterable<Department> departments(){
        return departmentService.findAll();
    }
    @GetMapping("/employees")
    public ModelAndView listEmployee(@RequestParam("s") Optional<String> s,@PageableDefault(value = 10) Pageable pageable){
        Page<Employee> employees;
        if(s.isPresent()){
            employees = employeeService.findAllByNameContaining(s.get(), pageable);
        } else {
            employees = employeeService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/employee/list");
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }
    @GetMapping("/create-employee")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView=new ModelAndView("/employee/create");
        modelAndView.addObject("employeeform",new EmployeeForm());
        return modelAndView;
    }
    @RequestMapping(value = "/save-employee", method = RequestMethod.POST)
    public ModelAndView saveEmployee(@ModelAttribute("employeeform") EmployeeForm employeeForm, BindingResult result){
        if (result.hasFieldErrors()){
            System.out.println("Result Error Occured" + result.getAllErrors());
        }
        employeeService.save(employeeForm);
            ModelAndView modelAndView=new ModelAndView("/employee/create");
            modelAndView.addObject("employee", new EmployeeForm());
            modelAndView.addObject("messager","New employee created successfully");
            return modelAndView;
    }
    @GetMapping("/edit-employee/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Employee employee=employeeService.findById(id);
        if (employee!=null) {
            ModelAndView modelAndView = new ModelAndView("/employee/edit");
            modelAndView.addObject("employee", employee);
            return modelAndView;
        } else {
            ModelAndView modelAndView=new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit-employee")
    public ModelAndView editEmployee(@ModelAttribute EmployeeForm employeeForm, BindingResult result){
        if (result.hasErrors()) {
            System.out.println("Result Error Occured" + result.getAllErrors());
        }
        employeeService.save(employeeForm);
        ModelAndView modelAndView = new ModelAndView("redirect:/employees");
        return modelAndView;
    }
    @GetMapping("/delete-employee/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Employee employee=employeeService.findById(id);
        if (employee!=null){
            ModelAndView modelAndView=new ModelAndView("/employee/delete");
            modelAndView.addObject("employee", employee);
            return modelAndView;
        }else {
            ModelAndView modelAndView=new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/delete-employee")
    public String deleteEmployee(@ModelAttribute EmployeeForm employeeForm){
        employeeService.remove(employeeForm.getId());
        return "redirect:/employees";
    }
    @GetMapping("/view-employee/{id}")
    public ModelAndView showViewForm(@PathVariable Long id){
        Employee employee=employeeService.findById(id);
        if (employee!=null){
            ModelAndView modelAndView=new ModelAndView("/employee/view");
            modelAndView.addObject("employee", employee);
            return modelAndView;
        }else {
            ModelAndView modelAndView=new ModelAndView("/error");
            return modelAndView;
        }
    }
}
