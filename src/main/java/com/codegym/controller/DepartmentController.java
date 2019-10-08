package com.codegym.controller;

import com.codegym.model.Department;
import com.codegym.model.Employee;
import com.codegym.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departments")
    public ModelAndView listDepartments(Pageable pageable) {

        Page<Department> departments = departmentService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/department/list");
        modelAndView.addObject("departments", departments);
        return modelAndView;
    }
    @GetMapping("/create-department")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/department/create");
        modelAndView.addObject("department", new Department());
        return modelAndView;
    }
    @PostMapping("/save-department")
    public ModelAndView saveDepartment(@ModelAttribute("department") Department department, BindingResult result){
        if (result.hasFieldErrors()){
            ModelAndView modelAndView= new ModelAndView("department/create");
            modelAndView.addObject("department", department);
            return modelAndView;
        }
        departmentService.save(department);
        ModelAndView modelAndView=new ModelAndView("/department/create");
        modelAndView.addObject("department",new Department());
        return modelAndView;
    }
    @GetMapping("/edit-department/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Department department = departmentService.findById(id);
        if(department != null) {
            ModelAndView modelAndView = new ModelAndView("/department/edit");
            modelAndView.addObject("department", department);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit-department")
    public ModelAndView updateDepartment(@ModelAttribute("department") Department department){
        departmentService.save(department);
        ModelAndView modelAndView=new ModelAndView("/department/edit");
        modelAndView.addObject("department",department);
        return modelAndView;
    }
    @GetMapping("/delete-department/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Department department = departmentService.findById(id);
        if(department != null) {
            ModelAndView modelAndView = new ModelAndView("/department/delete");
            modelAndView.addObject("department", department);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/delete-department")
    public String deleteDepartment(@RequestParam("id") Long id){
        departmentService.remove(id);
        return "redirect:/departments";
    }
    @GetMapping("/department/view/{id}")
    public ModelAndView viewDepartment(@PathVariable("id") Long id){
        Department department = departmentService.findById(id);
        if(department == null){
            return new ModelAndView("/error.404");
        }

        Iterable<Employee> employees = departmentService.findAllByDepartment(department);

        ModelAndView modelAndView = new ModelAndView("/department/view");
        modelAndView.addObject("department", department);
        modelAndView.addObject("employees", employees);
        return modelAndView;
    }


}
