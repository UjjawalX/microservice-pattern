package com.ujjawal.registration.controller;

import com.ujjawal.registration.model.Employee;
import com.ujjawal.registration.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository erepos;
    @GetMapping(path = "/")
    public Iterable<Employee> getAllEmployees() {
//        int i=1/0; to check if the class is discovered during startup
        return erepos.findAll();
    }
    @GetMapping(path = "/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return erepos.findById(id).get();
    }
    @PostMapping(path = "/")
    public void addEmployee(@RequestBody Employee eto) {
        erepos.save(eto);
    }
    @PutMapping(path = "/")
    public void modifyEmployee(@RequestBody Employee eto) {
        Optional<Employee> o = erepos.findById(eto.getId());
        if(o.isPresent())
            erepos.save(eto);
    }
    @DeleteMapping(path = "/{id}" )
    public void deleteEmployee(@PathVariable int id) {
        Optional<Employee> o = erepos.findById(id);
        if(o.isPresent())
            erepos.delete(o.get());
    }
    @GetMapping(path = "/lastname")
    public List<Employee> findLastname(@RequestParam String lname) {
        return erepos.findEmployeewithLastname(lname);
    }
    @GetMapping(path = "/firstname")
    public List<Employee> findFirstname(@RequestParam(value = "fname" , required = true) String firstname) {
        return erepos.findEmployeewithFirstname(firstname);

    }
    @GetMapping(path = "/firstnamelastname")
    public List<Employee> findFirstname(@RequestParam(value = "fname" , required = true) String firstname,@RequestParam(value = "lname", required = true) String lname) {

        return erepos.findEmplyeewithFirstorlastname(firstname, lname);

    }
}
